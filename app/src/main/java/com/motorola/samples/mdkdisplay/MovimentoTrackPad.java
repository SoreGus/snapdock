package com.motorola.samples.mdkdisplay;

import android.util.Log;

/**
 * Created by guilherme on 20/05/17.
 */

public class MovimentoTrackPad {
    private float posicaoX;
    private float posicaoY;
    private float posicaoX2;
    private float posicaoY2;

    private int larguraTela;
    private int alturaTela;

    public MovimentoTrackPad(int larguraTela, int alturaTela){
        this.larguraTela = larguraTela;
        this.alturaTela = alturaTela;
        //posicaoX = larguraTela/2;
        posicaoX = 0;
        posicaoY = 0;
        //posicaoY = alturaTela/2;
    }

    public void movimentarEixoX(float velocidadeX){
        posicaoX = posicaoX + velocidadeX;
        if(posicaoX2 <= larguraTela && posicaoX2 >= 0){
            posicaoX2 = posicaoX2 + velocidadeX;
        }
        if(posicaoX2 < 0){
            posicaoX2 = 0;
        }
        if(posicaoX2 > larguraTela){
            posicaoX2 = larguraTela;
        }
    }

    public void movimentarEixoY(float velocidadeY){
        posicaoY = posicaoY + velocidadeY;
        if(posicaoY2 <= alturaTela && posicaoY2 >= 0){
            posicaoY2 = posicaoY2 + velocidadeY;
        }
        if(posicaoY2 < 0){
            posicaoY2 = 0;
        }
        if(posicaoY2 > alturaTela){
            posicaoY2 = alturaTela;
        }
    }



    public float[] obterCoordenadas(){
        return new float[] {posicaoX, posicaoY};
    }


    public float[] obterCoordenadas2(){
        return new float[] {posicaoX2, posicaoY2};
    }
}