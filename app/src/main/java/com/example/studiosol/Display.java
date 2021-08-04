package com.example.studiosol;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/*
 * Classe criada para tratamento exclusido do display de 7 segmentos.
 *
 * O display foi criado utilizando 7 botões, para facilidade de implementação e layout,
 * dispostos no formato padrão de um display LED de 7 segmentos.
 *
 * Esta classe recebe as referencias dos botões a serem manipulados e
 * possui funções para edição dos mesmos:
 *
 * - setCor: Seta a cor selecionada pelo usuário para o display. Por padrão a cor é preta.
 * - zerarDisplay: Colore todos os segmentos para cinza, removendo qualquer número mostrado.
 * - esconderDisplay: Oculta todos os segmentos do display recebido como parametro.
 * - mostrarDisplay: Torna visível todos os segmentos do display recebido como parametro.
 * - formatarDisplay: Colore os segmentos específicos para representar cada número, de 0 a 9.
 */
public class Display {

    private ImageView a, b, c, d, e, f, g;
    private int corRecebida = Color.BLACK;

    public Display(ImageView a, ImageView b, ImageView c, ImageView d, ImageView e, ImageView f, ImageView g) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
    }

    public void setCor(int corRecebida){
        this.corRecebida = corRecebida;
    }

    public void zerarDisplay() {
        a.setColorFilter(Color.GRAY);
        b.setColorFilter(Color.GRAY);
        c.setColorFilter(Color.GRAY);
        d.setColorFilter(Color.GRAY);
        e.setColorFilter(Color.GRAY);
        f.setColorFilter(Color.GRAY);
        g.setColorFilter(Color.GRAY);
    }

    public void esconderDisplay() {
        a.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);
        d.setVisibility(View.INVISIBLE);
        e.setVisibility(View.INVISIBLE);
        f.setVisibility(View.INVISIBLE);
        g.setVisibility(View.INVISIBLE);
    }

    public void mostrarDisplay() {
        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);
        f.setVisibility(View.VISIBLE);
        g.setVisibility(View.VISIBLE);
    }

    public void formatarDisplay(char caractere) {
        /*A função recebe o número que o display deverá mostrar.
        * A relação entre quais segmentos mostrar de acordo com
        * o número informado foi encontrada no link:
        * "https://athoselectronics.com/display-de-7-segmentos-led/"
         */

        switch (caractere) {
            case '0':
                a.setColorFilter(corRecebida);
                b.setColorFilter(corRecebida);
                c.setColorFilter(corRecebida);
                d.setColorFilter(corRecebida);
                e.setColorFilter(corRecebida);
                f.setColorFilter(corRecebida);
                break;
            case '1':
                b.setColorFilter(corRecebida);
                c.setColorFilter(corRecebida);
                break;
            case '2':
                a.setColorFilter(corRecebida);
                b.setColorFilter(corRecebida);
                d.setColorFilter(corRecebida);
                e.setColorFilter(corRecebida);
                g.setColorFilter(corRecebida);
                break;
            case '3':
                a.setColorFilter(corRecebida);
                b.setColorFilter(corRecebida);
                c.setColorFilter(corRecebida);
                d.setColorFilter(corRecebida);
                g.setColorFilter(corRecebida);
                break;
            case '4':
                b.setColorFilter(corRecebida);
                c.setColorFilter(corRecebida);
                f.setColorFilter(corRecebida);
                g.setColorFilter(corRecebida);
                break;
            case '5':
                a.setColorFilter(corRecebida);
                c.setColorFilter(corRecebida);
                d.setColorFilter(corRecebida);
                f.setColorFilter(corRecebida);
                g.setColorFilter(corRecebida);
                break;
            case '6':
                a.setColorFilter(corRecebida);
                c.setColorFilter(corRecebida);
                d.setColorFilter(corRecebida);
                e.setColorFilter(corRecebida);
                f.setColorFilter(corRecebida);
                g.setColorFilter(corRecebida);
                break;
            case '7':
                a.setColorFilter(corRecebida);
                b.setColorFilter(corRecebida);
                c.setColorFilter(corRecebida);
                break;
            case '8':
                a.setColorFilter(corRecebida);
                b.setColorFilter(corRecebida);
                c.setColorFilter(corRecebida);
                d.setColorFilter(corRecebida);
                e.setColorFilter(corRecebida);
                f.setColorFilter(corRecebida);
                g.setColorFilter(corRecebida);
                break;
            case '9':
                a.setColorFilter(corRecebida);
                b.setColorFilter(corRecebida);
                c.setColorFilter(corRecebida);
                f.setColorFilter(corRecebida);
                g.setColorFilter(corRecebida);
                break;
        }

    }

}
