
# 🔐 Backend Login JWT - Spring Boot

Este es un ejemplo de un backend de autenticación de usuarios desarrollado en **Spring Boot** con soporte para **registro**, **inicio de sesión** y **verificación por código enviado por email**, utilizando **JWT (JSON Web Tokens)** para manejar la autenticación.

## 📦 Características

- Registro de usuarios con envío de código de verificación por email
- Inicio de sesión con validación de credenciales
- Generación y validación de tokens JWT
- Verificación de cuenta mediante código enviado
- Reenvío de código de verificación
- Persistencia de usuarios en base de datos PostgreSQL
- Contraseñas encriptadas con BCrypt

## 🛠️ Tecnologías utilizadas

- Java 17+
- Spring Boot 3+
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT
- Jakarta Validation
- HikariCP
- Lombok
- Mail (JavaMailSender)

## 📂 Estructura del proyecto

src/
├── config/                  # Configuración de seguridad y JWT
├── controller/              # Controladores REST para auth
├── dto/                     # Data Transfer Objects
├── model/                   # Entidades JPA
├── repositories/            # Interfaces JPA
├── services/                # Lógica de negocio
└── DemoApplication.java     # Punto de entrada

## ⚙️ Requisitos

- Java 17+
- PostgreSQL
- Gradle

## 🧪 Endpoints principales

### POST `/api/v1/auth/register`

Registrar un nuevo usuario. El usuario recibirá un código de verificación por email.

{
  "username": "juanpasderez",
  "email": "juan@example.com",
  "password": "12345678"
}

### POST `/api/v1/auth/login`

Autenticación con email y contraseña. Devuelve el token JWT si es exitoso.

{
  "email": "juan@example.com",
  "password": "12345678"
}

### POST `/api/v1/auth/verify`

Verifica la cuenta de usuario usando el código enviado por email.

{
  "email": "juan@example.com",
  "verificationCode": "123456"
}

### POST `/api/v1/auth/resend-code`

Reenvía el código de verificación por email.

{
  "email": "juan@example.com"
}

## 📧 Configuración de correo

Asegúrate de tener configuradas las siguientes propiedades en `application.properties` o `application.yml`:

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu_correo@gmail.com
spring.mail.password=tu_contraseña
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

> ⚠️ Si usas Gmail, es necesario generar una contraseña de aplicación. Puedes generar una en https://myaccount.google.com/apppasswords
IMPORTANTE: Para poder crear aplicaciones dentro de la cuenta de Gmail, es necesario tener activado el doble factor de autenticacion

## 🔐 JWT

El backend genera un token JWT al hacer login. Puedes proteger rutas usando filtros de Spring Security. El token se debe incluir en las solicitudes con:

Authorization: Bearer <token>

## 🧾 Base de datos

El proyecto usa PostgreSQL. Asegúrate de configurar en `application.properties`:

spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_bd
spring.datasource.username=usuario
spring.datasource.password=contraseña

## 🚀 Cómo probar el proyecto

# Clonar el repo
git clone https://github.com/zirconsol/backend-login-jwt.git
cd backend-login-jwt

# Compilar y correr
./mvnw spring-boot:run

## 🧪 Testeo

Puedes testear los endpoints usando Postman, Thunder Client, Insomnia u otra herramienta similar.
