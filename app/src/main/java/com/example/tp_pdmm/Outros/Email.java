package com.example.tp_pdmm.Outros;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

public class Email {

    private String[] Para;
    private String Assunto;
    private String Mensagem;

    public Email(String[] para, String assunto, String mensagem) {
        setPara(para);
        setAssunto(assunto);
        setMensagem(mensagem);
    }

    public String[] getPara() {
        return Para;
    }

    public void setPara(String[] para) {
        Para = para;
    }

    private String getAllPara() {
        return TextUtils.join(";", getPara());
    }

    public String getAssunto() {
        return Assunto;
    }

    public void setAssunto(String assunto) {
        Assunto = assunto;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }

    private Uri SetEmail() {
        return Uri.parse("mailto:?subject=" + getAssunto() + "&body=" + getMensagem() + "&to=" + getPara());
    }


    public void SendEmail(Context context) {
        Intent mailIntent = new Intent(Intent.ACTION_VIEW);
        mailIntent.setData(SetEmail());
        context.startActivity(Intent.createChooser(mailIntent, "Send mail..."));
    }
}
