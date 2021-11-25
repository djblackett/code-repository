# code-repository

This project is a server for sharing code snippets. You can upload and retrieve snippets of code that are persisted in a database. You can retrieve a specific code snippet by its UUID, or you can get a list of most recent code snippets. Making requests to endpoints beginning in `/api/` will return the code as JSON. Otherwise, it will be returned as html. There are options for limiting access to the code snippets. If you set the `time` and/or `views` property in the body of the POST request, you can set the number of seconds until the code snippet is automatically deleted, and/or the number of times it can be viewed before it is deleted. Snippets that are set with one of the properties will not be returned in requests made to the `/latest` endpoint. 


`GET /api/code/N` should return JSON with the N-th uploaded code snippet.
`GET /code/N` should return HTML that contains the N-th uploaded code snippet.
`POST /api/code/new` should take a JSON object with a single field code, use it as the current code snippet, and return JSON with a single field id. ID is the UUID of the code snippet that can help you access it via the endpoint `GET /code/N`.
`GET /code/new` should be the same as in the second and third stages.
`GET /api/code/latest` should return a JSON array with 10 most recently uploaded code snippets sorted from the newest to the oldest.
`GET /code/latest` should return HTML that contains 10 most recently uploaded code snippets. Use the title Latest for this page.

## Examples
In the following examples, consider that no code snippets have been uploaded beforehand.

Blank answers for responses are for images of rendered html that have yet to be added. Coming soon. 

### Example 1

Request `POST /api/code/new` with the following body:
```
{
    "code": "class Code { ...",
    "time": 0,
    "views": 0
}
```
Response: `{ "id" : "7dc53df5-703e-49b3-8670-b1c468f47f1f" }`.

Request `POST /api/code/new` with the following body:
```
{
    "code": "public static void ...",
    "time": 0,
    "views": 0
}
```
Response: `{ "id" : "e6780274-c41c-4ab4-bde6-b32c18b4c489" }`.

Request `POST /api/code/new` with the following body:
```
{
    "code": "Secret code",
    "time": 5000,
    "views": 5
}
```
Response: `{ "id" : "2187c46e-03ba-4b3a-828b-963466ea348c" }`.

### Example 2

Request: `GET /api/code/2187c46e-03ba-4b3a-828b-963466ea348c`

Response:
```
{
    "code": "Secret code",
    "date": "2020/05/05 12:01:45",
    "time": 4995,
    "views": 4
}
```
Another request `GET /api/code/2187c46e-03ba-4b3a-828b-963466ea348c`

Response:
```
{
    "code": "Secret code",
    "date": "2020/05/05 12:01:45",
    "time": 4991,
    "views": 3
}
```
### Example 3

Request: `GET /code/2187c46e-03ba-4b3a-828b-963466ea348c`

Response:



### Example 4

Request: `GET /api/code/latest`

Response:
```
[
    {
        "code": "public static void ...",
        "date": "2020/05/05 12:00:43",
        "time": 0,
        "views": 0
    },
    {
        "code": "class Code { ...",
        "date": "2020/05/05 11:59:12",
        "time": 0,
        "views": 0
    }
]
```
### Example 5

Request: `GET /code/latest`

Response:



### Example 6

Request: `GET /code/new`

Response:



