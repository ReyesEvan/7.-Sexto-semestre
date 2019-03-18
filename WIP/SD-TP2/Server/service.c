#include "server.h"
#include "../interface.h"

/**
 * Service 1
 */
int store (char * m) {
    FILE *fp = fopen("mensajes.txt", "a");

    fprintf(fp, "%s\n", m);
    fclose(fp);
    return strlen(m);
}

/**
 * Service 2
 */
int sum (int a, int b) {
    return a + b;
}

/**
 * Service 3
 */
void sumr (int * a, int * b, int * c) {
    *c = *a + *b;
}

void struct_function (struct person * p) {
    strcpy(p->name, "Andres");
    p->age = 21;
    strcpy(p->addr.street, "Camino Real");
    p->addr.number = 1105;
    strcpy(p->addr.city, "Puebla");

}

int search_data (struct person * p, char * name) {
    int found = 0;
    db = fopen("db", "r");

    if (db == NULL) {
        puts ("Problem while reading the file, check out and try again");
        return -1;
    }
    
    char c;     //caracter para leer el archivo un caracter a la vez
    struct person temp;
    
    c = fgetc(db);
    
    while (c != EOF && found == 0) {
        ungetc(c, db);
        
        if (fgets(temp.name, 30, db) == NULL) {
            printf("error in the contents of the file, data must be in the following order: \nName\nAge\nStreet\nNumber\nCity\n");
            break;
        }
        else {
            temp.name[strcspn(temp.name, "\r\n")] = 0;
            printf ("Name readed: %s\n", temp.name);
        }

        if (fscanf(db, "%d", &temp.age) != 1) {
            printf("error in the contents of the file, data must be in the following order: \nName\nAge\nStreet\nNumber\nCity\n");
            break;
        }
        else {
            printf ("Age readed: %d\n", temp.age);
        }
        
        fgetc(db);

        if (fgets(temp.addr.street, 50, db) == NULL) {
            printf("error in the contents of the file, data must be in the following order: \nName\nAge\nStreet\nNumber\nCity\n");
            break;
        }
        else {
            temp.addr.street[strcspn(temp.addr.street, "\r\n")] = 0;
            printf ("Street readed: %s\n", temp.addr.street);
        }

        if (fscanf(db, "%d", &temp.addr.number) != 1) {
            printf("error in the contents of the file, data must be in the following order: \nName\nAge\nStreet\nNumber\nCity\n");
            break;
        }
        else {
            printf ("Number readed: %d\n", temp.addr.number);
        }

        fgetc(db);
        
        if (fgets(temp.addr.city, 30, db) == NULL) {
            printf("error in the contents of the file, data must be in the following order: \nName\nAge\nStreet\nNumber\nCity\n");
            break;
        }
        else {
            temp.addr.city[strcspn(temp.addr.city, "\r\n")] = 0;
            printf ("City readed: %s\n", temp.addr.city);
        }

        found = strcmp(temp.name, name) == 0 ? 1 : 0;

        c = fgetc(db);
    }


    if (found) {
        strcpy(p->name, temp.name);
        p->age = temp.age;
        strcpy(p->addr.street, temp.addr.street);
        p->addr.number = temp.addr.number;
        strcpy(p->addr.city, temp.addr.city);
    }

    fclose(db);

    return found;
}