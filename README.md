# College Registrar Services REST API

This project is a REST API mirroring the operations performed by a college registrar office as described below.

## DB Diagram
<p align="center"><img src="/diagram.png" width="700px"></p>

## REST API
### Courses
<details>
<summary>Get List of Courses</summary>

#### Request
`GET /courses/`
`GET /courses?subject=SUBJECT_CODE`
    
    curl -i -H 'Accept: application/json' http://localhost:8080/courses?subject=ENGL

#### Response

    HTTP/1.1 200 OK
    Date: Sun, 16 Feb 2020 17:43:06 GMT
    Status: 200 OK
    Content-Type: application/json;charset=UTF-8
   
```Json
[]
```
    
</details>

<details>
<summary>Get Specific Course</summary>

#### Request
`GET /courses/:id`
    
    curl -i -H 'Accept: application/json' http://localhost:8080/courses/153

#### Response

    HTTP/1.1 200 OK
    Date: Sun, 16 Feb 2020 17:43:06 GMT
    Status: 200 OK
    Content-Type: application/json;charset=UTF-8

```Json
[
  {
    "id": 10016,
    "courseCode": "CISC-3130",
    "subject": {
      "id": 150,
      "subjectName": "Computer and Information Science",
      "subjectCode": "CISC"
    },
    "courseName": "Data Structures",
    "courseLevel": 3130,
    "units": 4.0,
    "prerequisites": "CISC-3115"
  }
]
```
    
</details>

### Instructors
<details>
<summary>Get List of Instructors</summary>

#### Request
`GET /instructors`
`GET /instructors?subject=SUBJECT_CODE`
`GET /instructors?lastName="LAST_NAME"`
    
    curl -i -H 'Accept: application/json' http://localhost:8080/instructors?subject=ENGL

#### Response

    HTTP/1.1 200 OK
    Date: Sun, 16 Feb 2020 17:43:06 GMT
    Status: 200 OK
    Content-Type: application/json;charset=UTF-8
    
```Json
[]
```
</details>

<details>
<summary>Get Specific Instructor</summary>

#### Request
`GET /instructors/:id`
    
    curl -i -H 'Accept: application/json' http://localhost:8080/instructors/98569604502126597

#### Response

    HTTP/1.1 200 OK
    Date: Sun, 16 Feb 2020 17:43:06 GMT
    Status: 200 OK
    Content-Type: application/json;charset=UTF-8
    
```Json
[
  {
    "id": 98569604502126597,
    "department": {
      "id": 120,
      "subjectName": "English",
      "subjectCode": "ENGL"
    },
    "firstName": "Ronald",
    "lastName": "Powell"
  }
]
```
</details>

### Majors
<details>
<summary>Get List of Majors</summary>

#### Request
`GET /majors`
`GET /majors?degree=DEGREE_NAME`
    
    curl -i -H 'Accept: application/json' http://localhost:8080/majors

#### Response

    HTTP/1.1 200 OK
    Date: Sun, 16 Feb 2020 17:43:06 GMT
    Status: 200 OK
    Content-Type: application/json;charset=UTF-8

```Json
[
  {
    "id": 102,
    "degree": "BACHELOR",
    "name": "Computer and Information Systems"
  },
  {
    "id": 100,
    "degree": "BACHELOR",
    "name": "Computer Science"
  },
  {
    "id": 101,
    "degree": "MASTER",
    "name": "Computer Science"
  }
]
```
    
</details>

### Semesters
<details>
<summary>Get List of Semesters</summary>

#### Request
`GET /semesters?active=BOOLEAN`
    
    curl -i -H 'Accept: application/json' http://localhost:8080/semesters?active=true

#### Response

    HTTP/1.1 200 OK
    Date: Sun, 16 Feb 2020 17:43:06 GMT
    Status: 200 OK
    Content-Type: application/json;charset=UTF-8

```Json
[
  {
    "id": 7,
    "startDate": "2019-08-27",
    "endDate": "2019-12-20",
    "semester": "FALL"
  },
  {
    "id": 8,
    "startDate": "2020-01-27",
    "endDate": "2020-05-20",
    "semester": "SPRING"
  }
]
```
</details>


### Students
<details>
<summary>Get List of Students</summary>

#### Request
`GET /students/`
`GET /students?lastName=LAST_NAME`
`GET /students?major=MAJOR_ID`
    
    curl -i -H 'Accept: application/json' http://localhost:8080/students?major=100

#### Response

    HTTP/1.1 200 OK
    Date: Sun, 16 Feb 2020 17:43:06 GMT
    Status: 200 OK
    Content-Type: application/json;charset=UTF-8

```Json
[]
```
</details>

<details>
<summary>Get Specific Student</summary>

#### Request
`GET /students/:id`
    
    curl -i -H 'Accept: application/json' http://localhost:8080/students/98579719267549188

#### Response

    HTTP/1.1 200 OK
    Date: Sun, 16 Feb 2020 17:43:06 GMT
    Status: 200 OK
    Content-Type: application/json;charset=UTF-8
    
```Json
{
  "id": 98579719267549188,
  "email": "YUE.WAN-YUNG9188@COLLEGE.EDU",
  "firstName": "Yue",
  "lastName": "Wan-Yung",
  "gender": "FEMALE",
  "dateOfBirth": "1994-12-14T05:00:00.000+0000",
  "major": {
    "id": 100,
    "degree": "BACHELOR",
    "name": "Computer Science"
  },
  "gpa": 3.62,
  "earnedCredits": 49,
  "attemptedCredits": 62
}
```
</details>

<details>
<summary>Get List of Enrolled Classes</summary>

#### Request
`GET /students/:studentId/classes/enrolled`
    
    curl -i -H 'Accept: application/json' http://localhost:8080/students/98579719267549188/classes/enrolled

#### Response

    HTTP/1.1 200 OK
    Date: Sun, 16 Feb 2020 17:43:06 GMT
    Status: 200 OK
    Content-Type: application/json;charset=UTF-8
    
```Json
[]
```
</details>

<details>
<summary>Enroll to Classes</summary>

#### Request
`POST /students/:studentId/classes/enrolled`
    
    curl -i -H 'Accept: application/json' http://localhost:8080/students/98579719267549188/classes/enrolled

#### Response

    HTTP/1.1 201 OK
    Date: Sun, 16 Feb 2020 17:43:06 GMT
    Status: 200 OK
    Content-Type: application/json;charset=UTF-8

```Json
[]
```
</details>

<details>
<summary>Drop Classes</summary>

#### Request
`DELETE /students/:studentId/classes/drop?id=CLASS_ID`
    
    curl -i -H 'Accept: application/json' http://localhost:8080/students/98579719267549188/classes/drop?id=10000000

#### Response

    HTTP/1.1 200 OK
    Date: Sun, 16 Feb 2020 17:43:06 GMT
    Status: 200 OK
    Content-Type: application/json;charset=UTF-8

```Json
[]
```
</details>

<details>
<summary>Get List of Completed Courses</summary>

#### Request
`GET /students/:studentId/classes/history`
    
    curl -i -H 'Accept: application/json' http://localhost:8080/students/98579719267549188/classes/history

#### Response

    HTTP/1.1 200 OK
    Date: Sun, 16 Feb 2020 17:43:06 GMT
    Status: 200 OK
    Content-Type: application/json;charset=UTF-8

```Json
[]
```
</details>

### Subjects
<details>
<summary>Get List of Subjects</summary>

#### Request
`GET /subjects/`
    
    curl -i -H 'Accept: application/json' http://localhost:8080/subjects

#### Response

    HTTP/1.1 200 OK
    Date: Sun, 16 Feb 2020 17:43:06 GMT
    Status: 200 OK
    Content-Type: application/json;charset=UTF-8

```Json
[
  {
    "id": 120,
    "subjectName": "English",
    "subjectCode": "ENGL"
  },
  {
    "id": 153,
    "subjectName": "Mathematics",
    "subjectCode": "MATH"
  }
]
```
    
</details>
