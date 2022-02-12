#include <stdlib.h>
#include <graphics.h>
#include <ctype.h>
#include <math.h>
using namespace std;

struct Cember{
    double merkez_x, merkez_y;
    double yaricap;
};

void menuYaz(){
    printf("\n");
    printf("1-Noktalari Goster\n");
    printf("2-En kucuk cemberi ciz\n");
    printf("3-Noktalardan gecen egriyi ciz\n");
    printf("9-Cikis\n");
    printf("Secim : ");
}

void int_to_char(char text[],int yazdirilacak){
    int k = 0; // verilen char dizisinin hangi index'ine atama yapılacağını yönetir.
    if (yazdirilacak < 0){
        text[0] = '-';
        k += 1;
        yazdirilacak *= -1;
    }
    if (yazdirilacak >= 10){
        text[k] = (yazdirilacak / 10) + '0';
        k += 1;
    }
    text[k] = (yazdirilacak % 10) + '0';
    k += 1;
    text[k] = '\0';
}

void nokta_duzenle(int *x, int *y, int ekran_x, int ekran_y, int genislik){
    int birim_genislik = ekran_x / (genislik*2); // koordinat sistemindeki birim karenin bir kenar uzunluğunun grafik ekranındaki karşılığı
    *x = (ekran_x/2) + (birim_genislik * (*x));
    *y = (ekran_y/2) - (birim_genislik * (*y));
}

void cember_bilgileri_yaz(Cember* cember,int ekran_x, int ekran_y, int genislik){
    // bu fonksiyon grafik ekranındaki çemberin bilgilerini koordinat sistemi formuna çevirir.
    int birim_genislik = ekran_x / (genislik*2);
    double x = (cember->merkez_x - (ekran_x/2)) / birim_genislik;
    double y = ekran_y - cember->merkez_y;
    y = (y - (ekran_y/2)) / birim_genislik;
    double yaricap = cember->yaricap / birim_genislik;
    printf("\n\nCemberin merkezinin x-y noktasi : %f %f\n", x, y);
    printf("Cemberin yaricapi : %f\n", yaricap);

}

void noktalari_duzenle(int noktalar[], int uzunluk, int ekran_x, int ekran_y, int genislik){
    // verilen noktaları grafik ekranındaki koordinat sistemine göre düzenler.
    for (int i=0; i<uzunluk; i+=2){
        nokta_duzenle(&noktalar[i],&noktalar[i+1], ekran_x, ekran_y , genislik);
    }
}

void noktalari_yazdir(int noktalar[], int uzunluk){
    // noktalari grafik ekranında gösterir.
    for (int i=0; i<uzunluk; i+=2){
        fillellipse(noktalar[i], noktalar[i+1], 4, 4);
    }
}

void koordinat_sistemi_ciz(int ekran_x, int ekran_y, int genislik){
    cleardevice();
    int birim_genislik = ekran_x / (genislik*2);
    line(0, ekran_y/2, ekran_x, ekran_y/2);
    line(ekran_x/2, 0, ekran_x/2, ekran_y);

    int yazdirilacak = -1 * genislik;
    for (int i=0; i<=ekran_x; i+=birim_genislik*5){
        char text[4];
        int_to_char(text, yazdirilacak);
        outtextxy(i, ekran_y/2+10, text);
        yazdirilacak += 5;
    }

    yazdirilacak = genislik;
    for (int i=0; i<=ekran_y; i+=birim_genislik*5){
        if (yazdirilacak == 0){
            yazdirilacak -= 5;
            continue;
        }
        char text[4];
        int_to_char(text, yazdirilacak);
        outtextxy(ekran_x/2 + 5, i, text);
        yazdirilacak -= 5;
    }
}

double mesafe_hesapla(double nokta1_x, double nokta1_y,double nokta2_x, double nokta2_y){
    return sqrt(pow(nokta1_x - nokta2_x, 2.0) + pow(nokta1_y - nokta2_y, 2.0));
}

int icinde_mi(Cember* cember, double x, double y){
    // verilen noktanin çemberin içinde veya üstünde olup olmadığını kontrol eder.
    if (mesafe_hesapla(cember->merkez_x, cember->merkez_y, x, y) <= cember->yaricap){
        return 1;
    }
    return 0;
}

void merkez_hesapla(Cember *cember,int nokta1_x, int nokta1_y,int nokta2_x, int nokta2_y){
    // gönderilen iki noktaya göre çemberin en merkezini ve yarıçapını hesaplar.
    cember->merkez_x = (nokta1_x + nokta2_x) / 2.0;
    cember->merkez_y = (nokta1_y + nokta2_y) / 2.0;
    cember->yaricap = mesafe_hesapla(cember->merkez_x, cember->merkez_y, nokta1_x, nokta1_y);
}

void merkez_hesapla(Cember* cember, double nokta_1x, double nokta_1y,double nokta_2x, double nokta_2y,double nokta_3x, double nokta_3y){
    // gönderilen üç noktaya göre çemberin merkezini ve yarıçapını hesaplar.
    double yardimci_1x,yardimci_1y,yardimci_2x,yardimci_2y;

    yardimci_1x = nokta_2x - nokta_1x;
    yardimci_1y = nokta_2y - nokta_1y;
    yardimci_2x = nokta_3x - nokta_1x;
    yardimci_2y = nokta_3y - nokta_1y;

    double b = pow(yardimci_1x, 2) + pow(yardimci_1y , 2);
    double c = pow(yardimci_2x, 2) + pow(yardimci_2y , 2);
    double d = yardimci_1x * yardimci_2y - yardimci_1y * yardimci_2x;
    double e = (yardimci_2y * b - yardimci_1y * c) / (2* d);
    double f = (yardimci_1x * c - yardimci_2x * b) / (2* d);

    cember->merkez_x = e + nokta_1x;
    cember->merkez_y = f + nokta_1y;
    cember->yaricap = mesafe_hesapla(cember->merkez_x, cember->merkez_y, nokta_1x, nokta_1y);
}

void cember_hesapla(Cember* cember, double nokta1_x, double nokta1_y,double nokta2_x, double nokta2_y,double nokta3_x, double nokta3_y){
    // verilen üç noktaya göre en küçük çemberi hesaplar.
    merkez_hesapla(cember,  nokta1_x,  nokta1_y, nokta2_x,  nokta2_y);
    if (icinde_mi(cember, nokta3_x, nokta3_y)){
        return;
    }

    merkez_hesapla(cember,  nokta1_x,  nokta1_y, nokta3_x,  nokta3_y);
    if (icinde_mi(cember, nokta2_x, nokta2_y)){
        return;
    }

    merkez_hesapla(cember,  nokta2_x,  nokta2_y, nokta3_x,  nokta3_y);
    if (icinde_mi(cember, nokta1_x, nokta1_y)){
        return;
    }

    merkez_hesapla(cember, nokta1_x, nokta1_y , nokta2_x,  nokta2_y, nokta3_x,  nokta3_y);
}

void en_kucuk_cember_bul(Cember* cember,int noktalar[], int uzunluk){
    // en küçük çevreleyen çemberi bulur.
    cember->yaricap = INT_MAX;
    for(int i=0; i<=uzunluk-4; i+=2){
        for (int j=i+2; j<=uzunluk-2 ; j+=2){
            for (int k=j+2; k<=uzunluk; k+=2){
                Cember cember2;
                int bulundu = 1;
                merkez_hesapla(&cember2, noktalar[i], noktalar[i+1], noktalar[j], noktalar[j+1], noktalar[k], noktalar[k+1]);
                for(int s = 0; s< uzunluk; s+=2){
                    if(s == i || s == j | s == k){
                        continue;
                    }
                    if (icinde_mi(&cember2, noktalar[s], noktalar[s+1]) == 0){
                        bulundu = 0;
                    }
                }
                if (bulundu && cember2.yaricap < cember->yaricap){
                    cember->merkez_x = cember2.merkez_x;
                    cember->merkez_y = cember2.merkez_y;
                    cember->yaricap = cember2.yaricap;
                }
            }
        }
    }
}

void egri_hesapla(double yeni_nokta[],int noktalar[],double katsayi){
    // noktalardan geçen eğrinin üzerindeki noktaları hesaplar.
    int nokta1, nokta2, nokta3, nokta4;
    nokta2 = int(katsayi) + 1;
    nokta3 = nokta2 + 1;
    nokta4 = nokta3 + 1;
    nokta1 = nokta2 - 1;
    katsayi = katsayi - int(katsayi);

    double etki1 = (-1*pow(katsayi, 3.0)) + (2.0 * pow(katsayi, 2.0)) - katsayi;
    double etki2 = (3.0 * pow(katsayi, 3.0)) - (5.0 * pow(katsayi, 2.0)) + 2.0;
    double etki3 = (-3.0 * pow(katsayi, 3.0)) + (4.0 * pow(katsayi, 2.0)) + katsayi;
    double etki4 = pow(katsayi, 3.0) - pow(katsayi, 2.0);

    yeni_nokta[0] =  0.5 * ((noktalar[nokta1*2] * etki1) + (noktalar[nokta2*2] * etki2) + (noktalar[nokta3*2] * etki3) + (noktalar[nokta4*2] * etki4));
    yeni_nokta[1] =  0.5 * ((noktalar[nokta1*2 +1] * etki1) + (noktalar[nokta2*2 +1] * etki2) + (noktalar[nokta3*2 +1] * etki3) + (noktalar[nokta4*2 +1] * etki4));
}

void egri_ciz(int noktalar[], int uzunluk, int ekran_x, int ekran_y){
    // noktalardan geçen eğriyi çizer.
    int nokta_sayisi = uzunluk/2;
    int noktalar2[uzunluk+4];
    noktalar2[0] = 0;
    noktalar2[1] = ekran_y/2;
    noktalar2[uzunluk+2] = ekran_x;
    noktalar2[uzunluk+3] = ekran_y/2;
    for (int i=2; i<uzunluk+2; i+=2){
        noktalar2[i] = noktalar[i-2];
        noktalar2[i+1] = noktalar[i-1];
    }
    double x = noktalar2[2];
    double y = noktalar2[3];
    for (double katsayi = 0.00 ; katsayi < double(nokta_sayisi+2) - 3.00; katsayi += 0.01){
        double yeni_nokta[2];
        egri_hesapla(yeni_nokta, noktalar2, katsayi);
        line(x, y, yeni_nokta[0], yeni_nokta[1]);
        x = yeni_nokta[0];
        y = yeni_nokta[1];
    }
}

void en_uzak_noktalar(int noktalar[], int uzunluk, int uzak_noktalar[]){
    // noktalar arasından birbirine en uzak iki noktayı hesaplar.
    double mesafe = -1;
    for (int i=0; i<uzunluk-2; i+=2){
        for(int j=i+2; j<uzunluk; j+=2){
            double a = mesafe_hesapla(noktalar[i],noktalar[i+1], noktalar[j], noktalar[j+1]);
            if ( a > mesafe ){
                mesafe = a;
                uzak_noktalar[0] = i;
                uzak_noktalar[1] = j;
            }
        }
    }
}

void cember_kontrol(Cember* cember, int noktalar[], int uzunluk){
    // 3 nokta ile çember bulma yönteminin istisnalarını kontrol eder.
    int uzak_noktalar[2];
    en_uzak_noktalar(noktalar, uzunluk, uzak_noktalar);
    Cember cember2;
    int nokta1 = uzak_noktalar[0], nokta2 = uzak_noktalar[1];
    merkez_hesapla(&cember2,noktalar[nokta1], noktalar[nokta1+1], noktalar[nokta2], noktalar[nokta2+1]);
    int gecerli_mi = 1;
    for (int i=0; i<uzunluk; i+=2){
        if (i == nokta1 || i == nokta2){
            continue;
        }
        if (icinde_mi(&cember2, noktalar[i], noktalar[i+1]) == 0){
            gecerli_mi = 0;
            break;
        }
    }
    if (gecerli_mi && cember2.yaricap < cember->yaricap){
        cember->merkez_x = cember2.merkez_x;
        cember->merkez_y = cember2.merkez_y;
        cember->yaricap = cember2.yaricap;
    }
}

void degistir(int* x, int* y){
    // iki değişkenin değerini adresleri yardımıyla değiştirir.
    int gecici = *x;
    *x = *y;
    *y = gecici;
}

void sirala(int noktalar[], int uzunluk){
    // bubble sort sıralaması kullanarak noktaları x değerlerine göre sıralar.
    for (int i=0; i<uzunluk-2; i+=2){
        for(int j=0; j<uzunluk-i-2; j+=2){
            if (noktalar[j] > noktalar[j+2]){
                degistir(&noktalar[j],&noktalar[j+2]);
                degistir(&noktalar[j+1],&noktalar[j+3]);
            }
        }
    }
}

int main()
{
    // dosya okuma işlemleri
    FILE* file;
    file = fopen("C:\\a.txt","r");
    if (file == NULL){
        printf("Dosya acilamadi");
    }
    else{
        char array[200];
        int i = 0; // kaçıncı indexte olduğunu belirtir.
        while(!feof(file)){
            fscanf(file,"%c",&array[i]);
            i++;
        }
        array[i] = '\0';

        int noktalar[200];
        int k = 0; // kaçıncı noktada olduğunu belirtir.
        for(int i=0; i<200; i++){
            if (array[i] == '\0'){
                break;
            }
            if (isdigit(array[i]) && isdigit(array[i+1])){
                noktalar[k] = 10*(array[i] - '0') + array[i+1] - '0';
                if(i>0 && array[i-1] == '-'){
                    noktalar[k] *= -1;
                }
                k++;
                i++; // iki karakter okuduğumuz için i 1 arttırıldı.
            }
            else if (isdigit(array[i])){
                noktalar[k] = array[i] - '0';
                if(i>0 && array[i-1] == '-'){
                    noktalar[k] *= -1;
                }
                k++;
            }
        }
        int uzunluk = k; // noktalar dizisinin uzunluğu
        int ekran_x = 640;
        int ekran_y = 640;
        int genislik = 20; // grafik ekranına çizilecek koordinat sisteminin genişliği
        // 20 değeri (-20,20) aralığını belirtir.
        sirala(noktalar, uzunluk);
        noktalari_duzenle(noktalar, uzunluk, ekran_x, ekran_y, genislik);
        int calistir = -1;
        initwindow(ekran_x+20, ekran_y+20,"Graph");
        char text[] = "Lutfen yapilacak bir islem secin";
        outtextxy(240, ekran_y/2, text);
        while(calistir != 9){
            menuYaz();
            scanf("%d", &calistir);
            switch(calistir){
            case 1:
                koordinat_sistemi_ciz(ekran_x,ekran_y,genislik);
                noktalari_yazdir(noktalar, uzunluk);
                system("cls"); // konsol ekranını temizler.
                break;
            case 2:
                koordinat_sistemi_ciz(ekran_x,ekran_y,genislik);
                noktalari_yazdir(noktalar, uzunluk);
                Cember cember;
                en_kucuk_cember_bul(&cember, noktalar, uzunluk);
                cember_kontrol(&cember, noktalar, uzunluk);
                circle(cember.merkez_x, cember.merkez_y, cember.yaricap);
                // merkezi ve yaricapi göster
                setcolor(MAGENTA);
                fillellipse(cember.merkez_x, cember.merkez_y, 5, 5);
                char yaricap[20];
                snprintf(yaricap, 20, "%f", (cember.yaricap/((ekran_x) / (genislik*2.0))));
                char yaricap2[22];
                yaricap2[0] = 'r';
                yaricap2[1] = '=';
                for(int i=0; i<20; i++){
                    yaricap2[i+2] = yaricap[i];
                }
                outtextxy(cember.merkez_x+6, cember.merkez_y, yaricap2);
                setcolor(MAGENTA);
                //
                system("cls");  // konsol ekranını temizler.
                cember_bilgileri_yaz(&cember, ekran_x, ekran_y, genislik);
                break;
            case 3:
                koordinat_sistemi_ciz(ekran_x,ekran_y,genislik);
                noktalari_yazdir(noktalar, uzunluk);
                egri_ciz(noktalar, uzunluk, ekran_x, ekran_y);
                system("cls");  // konsol ekranını temizler.
                break;
            case 9:
                break;
            default:
                system("cls");  // konsol ekranını temizler.
                printf("Yanlis secim yaptiniz!!! Lutfen gecerli bir secim yapiniz\n");
            }
        }

    }

    return 0;
}
