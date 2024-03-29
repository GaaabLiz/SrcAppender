# SrcAppender

# Introduzione
SrcAppender è un piccolo software multipiattaforma (Windows, Mac, Linux) scritto con kotlin multiplatform (Compose for desktop) che permette di unire molteplici file di testo in un solo; 
con la possibilità di aggiungere separatori personalizzati tra un file e l'altro. 

![example1.png](screen%2Fexample1.png)

Ho creato questo progetto per unire centinaia di file (con i codici sorgente di alcuni programmi) in un unico file in modo tale che potevo stamparlo e studiare facilmente gli algoritmi che vi erano all'interno.

# Presentazione del progetto

### Schermata principale

Nella schermata principale sono presenti quattro pulsanti:

- **Add file**: cliccando qui è possibile scegliere un file di testo all'interno del proprio sistema operativo e aggiungerlo all coda.
- **Add section separator**: qui è possibile aggiungere un separatore di sezione personalizzato.
- **Add file separator**: qui è possibile aggiungere un separatore che indica la divisione tra due file.
- **Create output file**: qui è possibile iniziare l'export del file finale (con tutti gli elementi selezionati) nella directory selezionata.

Cliccando su **Add section separator** verrà fuori il seguente dialog dove è possibile creare il separatore di sezione:

NOTA: attualmente non è possibile modificare i caratteri dei separatori.

### Esempio di funzionamento
Mostro qua sotto un possibile esempio di funzionamento del programma, illustrando le varie azioni compiute dall'utente.

**1**- Aggiunta di un separatore di file.

**2**- Aggiunta del seguente separatore di sezione:

![sep1.png](screen%2Fsep1.png)

**3**- Aggiunta del seguente file di testo: 

![sample1.png](screen%2Fsample1.png)

**4**- Aggiunta del seguente separatore di sezione:

![sep2.png](screen%2Fsep2.png)

**5**- Aggiunta del seguente file di testo:

![sample2.png](screen%2Fsample2.png)

Il file di output generato dal programma sarà:
![export.png](screen%2Fexport.png)