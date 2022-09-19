# Word Chain Game

Rest service created using Spring Boot, PostgreSQL and packaged in Docker

## Instalation

Go to docker folder

```bash
cd src/main/docker
```

And run script to build docker compose

```bash
chmod +x script.sh 
./script.sh
```

Or use the command

```bash
docker-compose up
```

## Usage

The program has one endpoint with two types of requests: GET and POST.

Use a POST request to "/" and pass in the body the words to be checked.

![image](https://drive.google.com/uc?export=view&id=1484G7nGd-VaCy4X8F5WMBmSeFGdhGDpl)

All words from the checked chains are stored in the database. 

If you send a GET request to "/" in the response will be all possible chains of words

![image](https://drive.google.com/uc?export=view&id=15BL__baJ_pH8u2OSGu0h_Jx86_cPf486)
