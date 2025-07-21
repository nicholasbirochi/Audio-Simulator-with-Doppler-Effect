# 🎧 Audio Simulator with Doppler Effect

A multidisciplinary project involving **Physics**, **Advanced Calculus**, **Object-Oriented Programming**, and **Databases**.  
Developed in the third semester of Computer Engineering.

🎥 **Watch the presentation (in Portuguese):** [YouTube Link](https://www.youtube.com/watch?v=SodHzU3wJBo)

---

## 👥 Collaborators

- [Vítor Braghittoni](https://github.com/VBraghittoni)
- [Nicolas Birochi](https://github.com/nicholasbirochi)
- [Henrico Birochi](https://github.com/henricobirochi)
- [Edgar Camacho](https://github.com/Edgarcsr)

---

## 🔬 Physics

Given details about the **sound source** and the **receptor**, the software generates audio simulating sound emission while accounting for the distortion caused by the **Doppler Effect**.

---

## ➗ Advanced Calculus

The **sine wave** used in audio generation is calculated using the **Maclaurin series**, ensuring a mathematical approach to waveform synthesis.

---

## 💻 Object-Oriented Programming

The program is structured using the **Object-Oriented Programming** paradigm, promoting modularity, readability, and ease of modification.

---

## 🗄️ Database

By using **SQL**, the software allows users to **save** information about:

- The environment
- The audio source
- The experiment

This data can be **reused** in future studies or experiments.

---

## ✨ Features

### 🎨 Minimalist Design

The software features a **simple** and **user-friendly** interface for seamless interaction.

### 🎼 Tone Selection

When creating an audio source, users can select from a **variety of tones**, offering flexibility in simulating different sound scenarios.

---

## � Prerequisites

Before running the project, make sure you have the following installed:

- **Java 22 or higher** - [Download here](https://www.oracle.com/java/technologies/downloads/)
- **Maven** - [Download here](https://maven.apache.org/download.cgi)
- **SQL Server** - [Download here](https://www.microsoft.com/en-us/sql-server/sql-server-downloads) (for database functionality)
- **Git** - [Download here](https://git-scm.com/downloads)

---

## 🛠️ Installation & Setup

### 1. Clone the repository

```bash
git clone https://github.com/nicholasbirochi/Audio-Simulator-with-Doppler-Effect
cd Audio-Simulator-with-Doppler-Effect
```

### 2. Database Setup (Optional)

If you want to use the database features:

1. Install and configure SQL Server
2. Execute the database scripts in order:
   ```sql
   -- Navigate to the Database folder and run:
   DataBasePBL.sql      -- Creates the database structure
   Procedures.sql       -- Creates stored procedures
   Triggers.sql         -- Creates database triggers
   ```

### 3. Navigate to the Project folder

```bash
cd Project
```

### 4. Install dependencies

```bash
mvn clean install
```

---

## 🚀 Running the Application

```bash
mvn clean javafx:run
```

---

## 🎮 Usage

1. **Launch the application** using one of the methods above
2. **Configure the sound source** - Select from various available timbres (Piano, Guitar, Trumpet, etc.)
3. **Set environment parameters** - Define the medium and conditions
4. **Configure Doppler effect** - Set source and receptor movement parameters
5. **Generate audio** - Click play to hear the simulated audio with Doppler effect
6. **Save experiments** - (Optional) Save your configurations to the database for future use

---

## 🔧 Troubleshooting

### Common Issues:

**Java Version Error:**

- Ensure you have Java 22 or higher installed
- Check your JAVA_HOME environment variable

**JavaFX Runtime Error:**

- The project uses the JavaFX Maven plugin to handle JavaFX dependencies
- Make sure to run with `mvn javafx:run` instead of `java -jar`

**Database Connection Error:**

- Verify SQL Server is running
- Check the connection string in `ConexaoBD.java`
- Ensure the database has been created using the provided SQL scripts

**Build Errors:**

- Run `mvn clean` before building
- Ensure all dependencies are downloaded: `mvn dependency:resolve`

---

## �🚀 Key Takeaway

This project integrates **physics simulation**, **mathematical modeling**, **programming paradigms**, and **data persistence** to create a comprehensive and educational audio simulator.
