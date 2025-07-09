### [GET] /concert

- 설명: 공연 정보를 단건 조회합니다.

- 요청:
    - Method: GET
    - URL: `{{url}}/api/concert`

- 응답: `200 OK`

```json
{
    "success": true,
    "message": "OK",
    "data": {
        "title": "title 입니다.",
        "description": "설명 입니다.",
        "date": "2020-01-01",
        "place": "azz",
        "artist": "artist"
    },
    "timestamp": "2025-07-09T10:57:02.7937593"
}
```

- 응답: `404 NOT FOUND`

```json
{
    "success": false,
    "message": "콘서트를 찾을 수 없습니다.",
    "data": {
        "status": 404,
        "errorCode": "CONCERT_NOT_FOUND",
        "message": "콘서트를 찾을 수 없습니다."
    },
    "timestamp": "2025-07-09T11:06:02.2443943"
}
```