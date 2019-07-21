# Programação de Dispositivos Móveis



| Atributo           | Valor                                                        |
| ------------------ | ------------------------------------------------------------ |
| Unidade Curricular | Programação de Dispositivos Móveis                           |
| Curso              | Mestrado em Engenharia Informática (MEI)                     |
| Instituição        | Instituto Politécnico do Cávado e do Ave (IPCA)              |
| Autoria            | Bruno Rodrigues (a9969@alunos.ipca.pt), Daniel Martins (a17497@alunos.ipca.pt) Ricardo Dias (a11597@alunos.ipca.pt) |
| Data               | 2019-06-28                                                   |

# Introdução e objetivos

O trabalho desenvolvido consiste na criação de uma aplicação móvel em android, e visa suportar o professor a organizar e gerir o ano lectivo, com visibilidade sobre as suas disciplinas, aulas, testes e ainda como forma de comunicação com os alunos.
Neste contexto, foram levantados os requisitos propostos pelo professor.

| id   | Requisitos                                                   | Classe | Estado                                                       |
| ---- | :----------------------------------------------------------- | ------ | ------------------------------------------------------------ |
| 1    | O professor deve poder entrar em sua sessão.                 | [P1]   | ![transferir](C:\Users\dan\Desktop\AndroidReport\transferir.png) |
| 2    | O professor deve ser capaz executar operações CRUD de anos, cursos,disciplinas, aulas, aulas especiais, eventos especiais. | [P1]   | ![transferir](C:\Users\dan\Desktop\AndroidReport\transferir.png) |
| 3    | O professor deve ser capaz de navegar nos anteriores anos, cursos, disciplinas e aulas. | [P1]   | ![transferir](C:\Users\dan\Desktop\AndroidReport\transferir.png) |
| 4    | O professor deve ser capaz de definir pessoas de contato com e-mails (todos os alunos ou pelo menos delegados). | [P1]   | ![transferir](C:\Users\dan\Desktop\AndroidReport\transferir.png) |
| 5    | Professor deve poder mudar de classe (sala ou datas / horas) e notificar (via e-mail) alunos e pessoas administrativas. | [P1]   | ![transferir](C:\Users\dan\Desktop\AndroidReport\transferir.png) |
| 6    | O professor deve poder copiar o conteúdo de uma disciplina para uma nova (independentemente do ano). | [P2]   |                                                              |
| 7    | O professor deve poder exportar o planejamento de uma disciplina para um (ou todos) de: Excel + texto simples ou Excel + PDF. | [P2]   |                                                              |
| 8    | O professor deve ser capaz de decidir se deseja ser notificado sobre aulas, eventos, reuniões de estudantes e afins. | [P2]   |                                                              |
| 9    | Login com o Google.                                          | [B1]   | ![transferir](C:\Users\dan\Desktop\AndroidReport\transferir.png) |
| 10   | Implementar uma ou várias estratégias de monetização.        | [B2]   |                                                              |
| 11   | Tornar a aplicação disponível no PlayStore ou através de um link | [B3]   | ![transferir](C:\Users\dan\Desktop\AndroidReport\transferir.png) |
| 12   | Crie um aplicativo simples para os alunos que interagem com o aplicativo de planejamento de professores para agendar compromissos de suporte. | [B4]   |                                                              |



## Arquitetura

A base de dados foi definido utilizar o REALM ,  usa muito pouco espaço em disco e foi introduzido nas aulas, o schema foi definido para responder às exigências do programa.

Para o desenvolvimento foi utilizado o Android Studio.



###	Modelo de dados

Neste ponto expõe se o modelo de dados:

![1562516629413](C:\Users\dan\Desktop\AndroidReport\1562516658648.png)

AnoModel

```java
@PrimaryKeyprivate 
int ID;
private String Descricao;
```

Aula Model

```java
    @PrimaryKey
    private Integer ID;
    private Date DataDeOcorrencia;
    private Integer Duracao;
    private String Sala;
    private String Tipo;
    private String Sumario;
```

CursoModel

```java
    private int ID;
    private String Descricao;
```
DisciplinaModel

```java
    @PrimaryKey
    private Integer ID;
    private String Nome;
    private String Curso;
    private Integer Anolectivo;
    private String Acronimo;
    private Integer Semestre;
   // public  String[] Principaistopicos = new String[20];
    private RealmList<AulaModel> aulaModels;
```

EventoModel

```java
@PrimaryKeyprivate 
int ID;
private String Descricao;
private Date DataInicio;
private int Duracao;
```

ProfessorModel

```java
@PrimaryKeyprivate 
int ID;
private String IdToken;
private String PhotoUrl;
private String Nome;
private String Email;
private RealmList<DisciplinaModel> disciplinaModels;
private String Contactos;
```

TipodeAula

```java
@PrimaryKeyprivate 
int ID;
private String Descricao;
```



## Desenvolvimento

Nesta parte iremos explorar a parte de desenvolvimento do projeto,será dividido por partes com breve demonstração da método de implementação.



- Objectivo id 1

Para o Login o professor pode usar a conta do google, 

![1562517229056](C:\Users\dan\AppData\Roaming\Typora\typora-user-images\1562517229056.png)

De forma a configurar o serviço de login é necessário configurar a aplicação a desenvolver no google developers, e registrar a chave SHA1 que pode ser obtida seguindo os seguintes passos.

![1562517941567](C:\Users\dan\Desktop\AndroidReport\1562517941567.png)

Após login no caso do registo não estar gravado na DB é automaticamente registado a conta, evitando criação de conta.



- Objectivo id 2

As operações de CRUD foram organizadas na secção "Entidades", para cada um dos modelos e um Geral de gestão de entidades útil para o acesso mais flexível e genérico.

![1562518519085](C:\Users\dan\Desktop\AndroidReport\1562518519085.png)



Ex: Entidade Ano

```java
public class Ano extends GestaoDeEntidades {
    public AnoModel entidade;

    public Ano(Context context) {
        this(context, null);
    }

    public Ano(Context context, AnoModel model) {
        entidade = model == null ? new AnoModel() : model;
        super.context = context;
    }

    @Override
    public void ExecuteCreatOrUpdate(Realm myRealm) {

        //Checks if the object already exists
        AnoModel findEntidade = myRealm.where(AnoModel.class).equalTo("ID", entidade.getID()).findFirst();

        if (findEntidade == null) {
            findEntidade = new AnoModel();
            findEntidade.setID(GetNextId(myRealm, AnoModel.class));
        }
        findEntidade.setDescricao(entidade.getDescricao());

        myRealm.insertOrUpdate(findEntidade);

    }

    @Override
    public void ExecuteDelete(Realm realm) {
        RealmResults<AnoModel> result = realm.where(AnoModel.class).equalTo("ID", entidade.getID()).findAll();

        if (result.size() == 0)
            Log.d("DataBase", "NO DATA FOUND TO DELETE");
        else
            result.deleteAllFromRealm();
    }

    @Override
    public void ExecuteRead(Realm myRealm) {
        ExecuteRead(myRealm, null);
    }

    @Override
    public void ExecuteRead(Realm myRealm, Integer ID) {
        setEntidade(myRealm.where(entidade.getClass()).equalTo("ID", ID == null ? entidade.getID() : ID).findFirst());
    }

    private void setEntidade(AnoModel entidade) {
        this.entidade = entidade != null ? entidade : new AnoModel();
    }
}
```



GestãodeEntidades

```java
public abstract class GestaoDeEntidades {
    private Realm realm;

    public Context context;

    public abstract void ExecuteCreatOrUpdate(Realm bgRealm);

    public abstract void ExecuteDelete(Realm realm);

    public abstract void ExecuteRead(Realm realm, Integer ID);

    public abstract void ExecuteRead(Realm realm);

    public void CreatOrUpdate() {
        realm = getRealm();

        realm.executeTransaction(r -> {
            ExecuteCreatOrUpdate(realm);
        });
        realm.close();
    }

    public void Delete() {
        Realm realm = getRealm();
        realm.executeTransaction(r -> {

            ExecuteDelete(realm);

        });
        realm.close();
    }

    public void Read() {
        Realm realm = getRealm();
        realm.executeTransaction(r -> {
            ExecuteRead(realm);
        });
        realm.close();
    }

    public Realm getRealm() {
        if (realm == null || realm.isClosed()) {
            try {
                realm = Realm.getDefaultInstance();
            } catch (IllegalStateException e) {
                if (e.getMessage().contains("Call `Realm.init(Context)` before creating a RealmConfiguration")) {
                    Realm.init(context);
                    realm = Realm.getDefaultInstance();
                } else {
                    throw e;
                }
            } catch (RealmMigrationNeededException r) {
                Realm.deleteRealm(getRealmConfiguration());
                realm = getRealm();
            }
        }

        return realm;
    }

    public static RealmConfiguration getRealmConfiguration() {
        return new RealmConfiguration.Builder().name("myrealm.realm").build();
    }

    Integer GetNextId(Realm realm, Class classToSearch) {
        Integer number = 0;
        try {
            number = realm.where(classToSearch).max("ID").intValue();
        } catch (Exception e) {
            Log.d("Erro on ID", "");
        } finally {
            return number == null ? 1 : ++number;
        }

    }

    public void Navegar(Enums.Navegar move, final int idRef) {
        Realm realm = getRealm();

        realm.executeTransaction(r -> {
            int idToSearch = 0;
            switch (move) {
                case Anterior: {
                    idToSearch = idRef - 1;
                    break;
                }
                case Seguinte: {
                    idToSearch = idRef + 1;
                    break;
                }
            }

            ExecuteRead(realm, idToSearch);
        });
        realm.close();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
```



De forma a navegar e aceder às operações CRUD foi utilizado ViewPager com as diferentes Fragments de cada model com respetiva interação.

| NavBar                                                    | ViewPager                                                 |
| --------------------------------------------------------- | --------------------------------------------------------- |
| ![](C:\Users\dan\Desktop\AndroidReport\1562518758895.png) | ![](C:\Users\dan\Desktop\AndroidReport\1562518786091.png) |

ViewPagerAdapter

```java
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putString("message","Fragment"+position);

        switch(position) {
            case 0:
                AulaFragment aulaFragment =new AulaFragment();
                position = position +1;
                aulaFragment.setArguments(bundle);
                return aulaFragment;
            case 1:
                CalendarioFragment calendarioFragment =new CalendarioFragment();
                position = position +1;
                calendarioFragment.setArguments(bundle);
                return calendarioFragment;
            case 2:
                CursoFragment cursoFragment =new CursoFragment();
                position = position +1;
                cursoFragment.setArguments(bundle);
                return cursoFragment;
            case 3:
                DisciplinaFragment disciplinaFragment =new DisciplinaFragment();
                position = position +1;
                disciplinaFragment.setArguments(bundle);
                return disciplinaFragment;
            case 4:
                EventoFragment eventoFragment =new EventoFragment();
                position = position +1;
                eventoFragment.setArguments(bundle);
                return eventoFragment;
            case 5:
                TurmasFragment turmasFragment =new TurmasFragment();
                position = position +1;
                turmasFragment.setArguments(bundle);
                return turmasFragment;
            case 6:
                TipoDeAulaFragment tipoDeAulaFragment =new TipoDeAulaFragment();
                position = position +1;
                tipoDeAulaFragment.setArguments(bundle);
                return tipoDeAulaFragment;
        }


        return null;
    }

    @Override
    public int getCount() {
        return 7;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

       String nome = null;

        switch(position) {
            case 0:
                nome= "Aula";
                return nome;
            case 1:
                nome= "Curso";
                return nome;
            case 2:
                nome= "Disc";
                return nome;
            case 3:
                nome= "Evento";
                return nome;
            case 4:
                nome= "Turma";
                return nome;
            case 5:
               nome= "TipoAula";
                return nome;
        }
        return null;
    }
}

```



- Objectivo id 3

Para a Navegação foi usado RecyclerView com de Ano->Disciplinas->Aulas .....



.... A concluir



- Objectivo id 4

O envio de email é realizado através do icon desenvolvido na class email.

![1562520969176](C:\Users\dan\AppData\Roaming\Typora\typora-user-images\1562520969176.png)Icon

```java
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
```







### Maquetes

Nesta secção iremos demonstrar os exemplos finais dos dashboards de navegação. 



..... A criar




# Conclusões e trabalho futuro

Após a conclusão deste trabalho foi possível concluir a grande evolução de todos os membros do grupo, totalmente inexperientes em programação android.

A nível das dificuldade que encontramos foi na utilização do git que gerou ainda alguns problemas locais após atualizações, muito devido às configurações do gradle. 

A navegação através de Recycler view foi também um desafio, considerando que a forma como está definida não é prática para desenvolvimento.

Este projeto permitiu cada membro do grupo ultrapassar o as suas próprias limitações e munir cada um de mais uma valência para o mercado de trabalho o que pode resultar em futuras oportunidades. 




# Referências

- https://developer.android.com/studio/intro
- https://developer.android.com/guide/topics/ui/layout/recyclerview
- https://realm.io/blog/realm-for-android/
