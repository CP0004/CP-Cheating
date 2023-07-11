//
// Created by Sagar Shah on 07/5/2021.
//

#ifndef COSMIC_MEMORY_H
#define COSMIC_MEMORY_H

#include <dirent.h>
#include <cstdio>
#include <cstring>
#include <cstdlib>
#include <string>
#include <fcntl.h>
#include "Process.h"

static uintptr_t libbase = 0;

int find_pid(const char *process_name)
{
    int id;
    pid_t pid = -1;
    DIR *dir;
    FILE *fp;
    char filename[32];
    char cmdline[256];

    struct dirent *entry;
    if (process_name == NULL)
    {
        return -1;
    }
    dir = opendir("/proc");
    if (dir == NULL)
    {
        return -1;
    }
    while ((entry = readdir(dir)) != NULL)
    {
        id = atoi(entry->d_name);
        if (id != 0)
        {
            sprintf(filename, "/proc/%d/cmdline", id);
            fp = fopen(filename, "r");
            if (fp)
            {
                fgets(cmdline, sizeof(cmdline), fp);
                fclose(fp);
                if (strcmp(process_name, cmdline) == 0)
                {
                    pid = id;
                    break;
                }
            }
        }
    }

    closedir(dir);
    return pid;
}

int isApkRunning(char *pkg_name)
{
    if (find_pid(pkg_name) != 0 && find_pid(pkg_name) > 0)
    {
        return 1;
    }
    return 0;
}

uintptr_t get_module_base(const char *module_name)
{
    FILE *fp;
    uintptr_t addr = 0;
    char filename[40], buffer[1024];
    snprintf(filename, sizeof(filename), "/proc/%d/maps", target_pid);
    fp = fopen(filename, "rt");
    if (fp != NULL)
    {
        while (fgets(buffer, sizeof(buffer), fp))
        {
            if (strstr(buffer, module_name))
            {
                addr = (uintptr_t)strtoul(buffer, NULL, 16);
                break;
            }
        }
        fclose(fp);
    }
    return addr;
}

uintptr_t getRealOffset(uintptr_t offset)
{
    if (libbase == 0)
    {
        LOGE("Error: Can't Find Base Addr for Real Offset");
        return 0;
    }
    return (libbase + offset);
}

int isValid64(uintptr_t addr)
{
    if (addr < 0x1000000000 || addr > 0xefffffffff || addr % sizeof(uintptr_t) != 0)
        return 0;
    return 1;
}

int isValid32(uintptr_t addr)
{
    if (addr < 0x10000000 || addr > 0xefffffff || addr % sizeof(uintptr_t) != 0)
        return 0;
    return 1;
}

template <typename T>
T Read(uintptr_t address)
{
    T data;
    vm_readv(reinterpret_cast<void *>(address), reinterpret_cast<void *>(&data), sizeof(T));
    return data;
}

char *ReadStr(uintptr_t address, int size)
{
    char *data = new char[size];
    for (int i = 0; i < size; i++)
    {
        vm_readv(reinterpret_cast<void *>(address + (sizeof(char) * i)), reinterpret_cast<void *>(data + i), sizeof(char));
        if (data[i] == 0x0)
        {
            break;
        }
    }
    return data;
}

uintptr_t getPtr(uintptr_t address)
{
    return Read<uintptr_t>(address);
}

template <typename T>
void memWrite(uintptr_t address, T value)
{
    std::string filename = "/proc/" + std::to_string(target_pid) + "/mem";
    int fd = open(filename.c_str(), O_WRONLY);
    if (fd != -1)
    {
        pwrite64(fd, &value, sizeof(T), address);
        close(fd);
    }
}

template <typename T>
void memWriteArray(uintptr_t address, T *ptr, unsigned int num)
{
    std::string filename = "/proc/" + std::to_string(target_pid) + "/mem";
    int fd = open(filename.c_str(), O_WRONLY);
    if (fd != -1)
    {
        pwrite64(fd, ptr, sizeof(T) * num, address);
        close(fd);
    }
}

template <typename T>
void Write(uintptr_t address, T value)
{
    pvme((void *)address, reinterpret_cast<void *>(&value), sizeof(T), true);
}

enum type
{
    TYPE_DWORD,
    TYPE_FLOAT
};

void WriteDword(uintptr_t address, int value)
{
    vm_writev(reinterpret_cast<void *>(address), reinterpret_cast<void *>(&value), 4);
}

void WriteFloat(uintptr_t address, float value)
{
    vm_writev(reinterpret_cast<void *>(address), reinterpret_cast<void *>(&value), 4);
}

void Write(uintptr_t address, const char *value, type TYPE)
{
    switch (TYPE)
    {
    case TYPE_DWORD:
        WriteDword(address, atoi(value));
        break;
    case TYPE_FLOAT:
        WriteFloat(address, atof(value));
        break;
    }
}

char *getNameByte(uintptr_t address)
{
    char static lj[64];
    memset(lj, 0, 64);
    unsigned short int nameI[32];
    pvm(address, nameI, sizeof(nameI));
    char s[10];
    int i;
    for (i = 0; i < 32; i++)
    {
        if (nameI[i] == 0)
            break;
        sprintf(s, "%d:", nameI[i]);
        strcat(lj, s);
    }
    lj[63] = '\0';

    return lj;
}

char *getText(uintptr_t addr)
{
    static char txt[42];
    memset(txt, 0, 42);
    char buff[41];
    pvm(addr + 4 + SIZE, &buff, 41);
    int i;
    for (i = 0; i < 41; i++)
    {
        if (buff[i] == 0)
            break;

        txt[i] = buff[i];

        if (buff[i] == 67 && i > 10)
            break;
    }
    txt[i + 1] = '\0';
    return txt;
}

#endif // COSMIC_MEMORY_H
