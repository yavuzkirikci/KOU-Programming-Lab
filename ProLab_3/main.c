#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

struct Node
{
    struct Node *sonraki;
    char kelime[100];
    int tekrar;
};

struct Node* yeniOlustur(int kac_defa_geciyor, char gecici[]){
    struct Node* yeni = (struct Node*) malloc(sizeof(struct Node));
    strcpy(yeni->kelime, gecici);
    yeni->tekrar = kac_defa_geciyor;
    return yeni;
}

struct Node* basaEkle(struct Node* bas, int kac_defa_geciyor, char gecici[]){
    struct Node* yeniPtr = yeniOlustur(kac_defa_geciyor, gecici);
    yeniPtr->sonraki = bas;
    bas = yeniPtr;
    return bas;
}

void sonaEkle(struct Node* son, int kac_defa_geciyor, char gecici[]){
    struct Node* yeniPtr = yeniOlustur(kac_defa_geciyor, gecici);
    yeniPtr->sonraki = NULL;
    son->sonraki = yeniPtr;
}

void arayaEkle(struct Node* birOnceki, struct Node* ptr, int kac_defa_geciyor, char gecici[]){
    struct Node* yeniPtr = yeniOlustur(kac_defa_geciyor, gecici);
    birOnceki->sonraki = yeniPtr;
    yeniPtr->sonraki = ptr;
}

void listeyiYazdir(struct Node* bas){
    struct Node* ptr = bas;
    int index = 1;
    while (ptr){
        printf("%d. %s:%d\n",index++, ptr->kelime, ptr->tekrar);
        ptr = ptr->sonraki;
    }
}

void to_upper(char array[]){
    for (int i = 0; i < 100; ++i) {
        if (array[i] == '\0')
            break;
        array[i] = toupper(array[i]);
    }
}

int main()
{
    FILE *file = fopen("D://file.txt", "r");
    FILE *file2 = fopen("D://cikti.txt", "w");
    char gecici[100];

    if (!file)
    {
        printf("Dosya bulunamadi\n");
        return 1;
    }
    struct Node* bas = NULL;
    int a = 0;
    while (fscanf(file, "%s", gecici) == 1)
    {
        // aradığımız eleman listede var mı
        to_upper(gecici);
        struct Node* ptr = bas;
        int listede_var_mi = 0;
        while (ptr){
            if (strcmp(ptr->kelime, gecici) == 0){
                listede_var_mi = 1;
                break;
            }
            ptr = ptr->sonraki;
        }
        if (listede_var_mi)
            continue;

        int pos = ftell(file);
        fseek(file, 0, SEEK_SET);

        // okuduğumuz kelime metinde kaç defa geçtiğini bulduk
        char gecici2[100];
        int kac_defa_geciyor = 0;
        while (fscanf(file, "%s", gecici2) == 1){
            to_upper(gecici2);
            if (strcmp(gecici2, gecici) == 0)
                kac_defa_geciyor += 1;
        }
        // listede hiç eleman olmama durumu
        if (bas == NULL){
            bas = (struct Node*) malloc (sizeof(struct Node));
            strcpy(bas->kelime, gecici);
            bas->sonraki = NULL;
            bas->tekrar = kac_defa_geciyor;
        }
        else{
            ptr = bas;
            struct Node* birOnceki = NULL;
            while (ptr != NULL){
                if (kac_defa_geciyor > ptr->tekrar)
                    break;
                birOnceki = ptr;
                ptr = ptr->sonraki;
            }

            // basa ekleme
            if (ptr == bas){
                bas = basaEkle(bas, kac_defa_geciyor, gecici);
            }
                // sona ekleme
            else if (ptr == NULL){
                sonaEkle(birOnceki, kac_defa_geciyor, gecici);
            }
                // araya ekleme
            else{
                arayaEkle(birOnceki, ptr, kac_defa_geciyor, gecici);
            }
        }

        fseek(file, pos, SEEK_SET);
    }
    listeyiYazdir(bas);
    struct Node* ptr = bas;
    int index = 1;
    while (ptr != NULL){
        fprintf(file2, "%d. %s:%d\n",index++,ptr->kelime, ptr->tekrar);
        ptr = ptr->sonraki;
    }
    fclose(file);
    fclose(file2);
    return 0;
}
