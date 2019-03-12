#include<stdio.h>

struct naipe {
    int valor;
    char palo;
};

struct naipe carta;

typedef struct {
    char a;
    char b;
    int i;
    char c;
    char d;
} MENSAJE;

MENSAJE mensaje = {'A', 'B', 0, 'C', 'D'};

int main (int argc, char **argv) {
    char *p = (char *)&mensaje;
    
    
    for (int in = 0; in < sizeof(mensaje); in++)
        printf("%i - %x: %i\n", in, (p+in), p[in]);
    
    
    printf("%c %c %i %c %c\n", mensaje.a, mensaje.b, mensaje.i, mensaje.c, mensaje.d);

    return 0;
    
}