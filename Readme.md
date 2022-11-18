# Event Organizer <a name = "header"></a>


<img src="https://thumbs.dreamstime.com/b/%D0%BA%D0%B0%D0%BB%D0%B5%D0%BD%D0%B4%D0%B0%D1%80%D1%8C-%D1%81%D0%BE%D0%B1%D1%8B%D1%82%D0%B8%D0%B9-%D0%BA%D0%BE%D0%BD%D1%86%D0%B5%D0%BF%D1%86%D0%B8%D1%8F-%D0%B4%D0%BB%D1%8F-%D0%BA%D0%B0%D0%BB%D0%B5%D0%BD%D0%B4%D0%B0%D1%80%D1%8F-%D1%81%D0%BE%D0%B1%D1%8B%D1%82%D0%B8%D0%B5-%D0%BB%D0%B8%D1%87%D0%BD%D1%8B%D0%B9-%D0%BE%D1%80%D0%B3%D0%B0%D0%BD%D0%B8%D0%B7%D0%B0%D1%82%D0%BE%D1%80-116902701.jpg" align="left" width="200" hspace="10" vspace="10">
Event Organizer is an application for organize and work with events.<br/> 



## Technologies <a name = "table"></a>
![Spring](https://img.shields.io/badge/-Spring-EAE5E9?style=for-the-badge&logo=Spring&logoColor=Green)
![Hibernate](https://img.shields.io/badge/-Hibernate-EAE5E9?style=for-the-badge&logo=Hibernate&logoColor=504099)
![PostgreSQL](https://img.shields.io/badge/-PostgreSQL-EAE5E9?style=for-the-badge&logo=PostgreSQL&logoColor=blue)
![Docker](https://img.shields.io/badge/-Docker-EAE5E9?style=for-the-badge&logo=Docker&logoColor=blue)
![Postman](https://img.shields.io/badge/-Postman-EAE5E9?style=for-the-badge&logo=Postman&logoColor=orange)



## Features <a name = "table"></a>
- Events
    - Add event
    - Change event
    - Delete event
    - View events list
    - View event by id
    - Filter events by letter
    - Filter events by time
    - Sort events by subject
    - Sort events by time
  - Sort events by organizer surname
- Locations
    - Add location
    - Change location
    - Delete location
    - View locations list
    - View location by id
- Organizers
  - Add organizer
  - Change organizer
  - Delete organizer
  - View organizers list
  - View organizer by id

## ✨ Demo
![demo.png](src/main/resources/demo.jpg)

## Requirements
You will need [Docker](https://docs.docker.com/) (with Compose plugin) installed and running on your machine.

We recommend using Docker Desktop on [macOS](https://docs.docker.com/desktop/install/mac-install/) /
[Windows](https://docs.docker.com/desktop/install/windows-install/) and using [Docker Server]
(https://docs.docker.com/engine/install/#server) distribution for your Linux distribution of choice. 
Use installation instructions provided in the links above.
  
## 🚀 Usage
- Clone project:
  ```sh
  git clone https://github.com/Evgeniy343/TaskModsen
  cd {project-name}
  ```
- Launch
  ```sh
  docker-compose up
  ```

  You will have the opportunity to use this application in Postman because application doesn't have a web interface. 

  In dockerfile generated file .jar and created a database and tables with used file init.sql.

## Troubleshooting
- Docker images built on your computer are not launching correctly and logging something along these lines: 
`Error response from daemon: user declined directory sharing {some_path}/init.sql`

  **Solution**: please go to docker Desktop Settings/Resources/File sharing/ and add a path to your file init.sql.

## Author

* GitHub: [@Zhendos](https://github.com/Evgeniy343)