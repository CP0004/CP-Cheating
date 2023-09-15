#ifndef COSMIC_SOCKET_H
#define COSMIC_SOCKET_H

#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <string>
#include <vector>
#include <errno.h>
#include <stdio.h>
#include <sys/un.h>

#define SOCKET_NAME "\0somethingf"
#define BACKLOG 8
int sock,clientD;
sockaddr_un addr_server;
char socket_name[108];

int Create() {
    int isCreated = (sock = socket(AF_UNIX, SOCK_STREAM,0)) >= 0;
    return isCreated;
}

void Close() {
    if (clientD > 0)
        close(clientD);
    if (sock > 0)
        close(sock);
}
int Accept() {
    if ((clientD = accept(sock, nullptr, nullptr)) < 0) {
        Close();
        return 0;
    }
    return 1;
}
//组合
int Bind() {
    memset(socket_name, 0, sizeof(socket_name));
    memcpy(&socket_name[0], "\0", 1);
    strcpy(&socket_name[1], SOCKET_NAME);

    memset(&addr_server, 0, sizeof(addr_server));
    addr_server.sun_family = AF_UNIX; // Unix Domain instead of AF_INET IP domain
    strncpy(addr_server.sun_path, socket_name, sizeof(addr_server.sun_path) - 1); // 108 char max
    int amit =bind(sock, (struct sockaddr *) &addr_server, sizeof(addr_server));
    if ( amit< 0) {
        Close();
        return 0;
    }
    return 1;
}
//监听
int Listen() {
    if (listen(sock, BACKLOG) < 0) {
        Close();
        return 0;
    }
    return 1;
}

//发送数据
int sendData(void *inData, size_t size) {
    char *buffer = (char *) inData;
    int numSent = 0;

    while (size) {
        do {
            numSent = write(clientD, buffer, size);
        } while (numSent == -1 && EINTR == errno);

        if (numSent <= 0) {
            Close();
            break;
        }

        size -= numSent;
        buffer += numSent;
    }
    return numSent;
}

//发送
int send(void* inData, size_t size) {
    uint32_t length = htonl(size);
    if(sendData(&length, sizeof(uint32_t)) <= 0){
        return 0;
    }
    return sendData(inData, size) > 0;
}

//读出数据
int recvData(void *outData, size_t size) {
    char *buffer = (char *) outData;
    int numReceived = 0;

    while (size) {
        do {
            numReceived = read(clientD, buffer, size);
        } while (numReceived == -1 && EINTR == errno);

        if (numReceived <= 0) {
            Close();
            break;
        }

        size -= numReceived;
        buffer += numReceived;
    }
    return numReceived;
}

size_t receive(void* outData) {
    uint32_t length = 0;
    int code = recvData(&length, sizeof(uint32_t));
    if(code > 0){
        length = ntohl(length);
        recvData(outData, static_cast<size_t>(length));
    }
    return length;
}

#endif //COSMIC_SOCKET_H
