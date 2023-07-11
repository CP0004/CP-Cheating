//
// Created by Sagar Shah on 07/5/2021.
//

#ifndef COSMIC_PROCESS_H
#define COSMIC_PROCESS_H

#include <sys/types.h>
#include <sys/uio.h>
#include <sys/syscall.h>
#include <unistd.h>

pid_t target_pid = -1;

#if defined(__arm__)
int process_vm_readv_syscall = 376;
int process_vm_writev_syscall = 377;
#elif defined(__aarch64__)
int process_vm_readv_syscall = 270;
int process_vm_writev_syscall = 271;
#elif defined(__i386__)
int process_vm_readv_syscall = 347;
int process_vm_writev_syscall = 348;
#else
int process_vm_readv_syscall = 310;
int process_vm_writev_syscall = 311;
#endif

ssize_t process_v33(pid_t _pid, const struct iovec *_local_iov, unsigned long _local_iov_count, const struct iovec *_remote_iov, unsigned long _remote_iov_count, unsigned long _flags, bool iswrite)
{
    return syscall((iswrite ? process_vm_writev_syscall : process_vm_readv_syscall), _pid, _local_iov, _local_iov_count, _remote_iov, _remote_iov_count, _flags);
}

ssize_t process_v(pid_t __pid, struct iovec *__local_iov, unsigned long __local_iov_count, struct iovec *__remote_iov, unsigned long __remote_iov_count, unsigned long __flags)
{
    return syscall(process_vm_readv_syscall, __pid, __local_iov, __local_iov_count, __remote_iov, __remote_iov_count, __flags);
}

bool pvm(void *address, void *buffer, size_t size, bool iswrite)
{
    struct iovec local[1];
    struct iovec remote[1];
    local[0].iov_base = buffer;
    local[0].iov_len = size;
    remote[0].iov_base = address;
    remote[0].iov_len = size;
    if (target_pid < 0)
    {
        return false;
    }
    ssize_t bytes = syscall((iswrite ? process_vm_writev_syscall : process_vm_readv_syscall), target_pid, local, 1, remote, 1, 0);
    return bytes == size;
}

bool vm_readv(void *address, void *buffer, size_t size)
{
    return pvm(address, buffer, size, false);
}

bool vm_writev(void *address, void *buffer, size_t size)
{
    return pvm(address, buffer, size, true);
}

bool VMRead(void *address, void *buffer, size_t size)
{
    struct iovec local[1];
    struct iovec remote[1];
    local[0].iov_base = buffer;
    local[0].iov_len = size;
    remote[0].iov_base = address;
    remote[0].iov_len = size;
    ssize_t bytes = syscall(process_vm_readv_syscall, target_pid, local, 1, remote, 1, 0);
    return bytes == size;
}

int pvm(uintptr_t address, void *buffer, int size)
{
    struct iovec local[1];
    struct iovec remote[1];

    local[0].iov_base = (void *)buffer;
    local[0].iov_len = size;
    remote[0].iov_base = (void *)address;
    remote[0].iov_len = size;
    ssize_t bytes = process_v(target_pid, local, 1, remote, 1, 0);
    return bytes == size;
}

bool VMWrite(uintptr_t address, void *buffer, size_t size)
{
    struct iovec local[1];
    struct iovec remote[1];
    local[0].iov_base = buffer;
    local[0].iov_len = size;
    remote[0].iov_base = (void *)address;
    remote[0].iov_len = size;
    ssize_t bytes = syscall(process_vm_writev_syscall, target_pid, local, 1, remote, 1, 0);
    return bytes == size;
}

bool pvme(void *address, void *buffer, size_t size, bool iswrite)
{
    struct iovec local[1];
    struct iovec remote[1];
    local[0].iov_base = buffer;
    local[0].iov_len = size;
    remote[0].iov_base = address;
    remote[0].iov_len = size;
    if (target_pid < 0)
    {
        return false;
    }
    ssize_t bytes = process_v33(target_pid, local, 1, remote, 1, 0, iswrite);
    if (bytes == size)
    {
        return true;
    }
    else
    {
        return false;
    }
}

#define SIZE sizeof(uintptr_t)

#endif // COSMIC_PROCESS_H
