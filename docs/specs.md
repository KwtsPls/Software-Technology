# Specs

## Back-end
Τεχνολογίες που χρησιμοποιήθηκαν: <br>
`Spring Boot`: Για το Back-end μαζί με `Spring Security, Spring Data JPA, Spring Hateoas, GSON` <br>
`MySql`: Για την αποθήκευση των δεδομένων σε βάση <br>
`Gradle` <br>
`Java 11`

## CLI-app
`Spring Shell`: `Spring Security, Spring Data JPA, Spring Hateoas, GSON` <br>
`Gradle` <br>
`Java 11`

## Front-end
`React`
`npm`

## Endpoints

`[GET] /users/{user_id}/projects`

Επιστρέφει όλα τα project στα οποία συμμετέχει ο χρήστης με id {user_id} σε μορφή json.<br><br>


`[GET] /projects/{project_id}/users`

Επιστρέφει όλους του χρήστες που συμμετέχουν στο project με id {project_id} σε μορφή json.<br><br>


`[GET] /developers/projects/{project_id}`

Επιστρέφει τους χρήστες που συμμετέχουν στο project με id {project_id} στην παρακάτω μορφή : <br><br>
[[user_id1,username1,accepted1,role1],[user_id2,username2,accepted2,role2],..].<br><br>


`[GET] /projects/{id}/user={username}`

Επιστρέφει "YES" αν ο χρήστης με {username} υπάρχει στο project με id {id} διαφορετικά επιστρέφει "NO".<br><br>

`[POST] /projects/create/{user_id}`

Δημιουργεί ένα project με project owner ton {user_id}. Το json που αναμένεται για να επιτύχει το request είναι της μορφής : <br><br>
{"title":string,"description":string,"status":"0/1","date_finished":?}<br><br>

`[GET] /users/name={username}`

Επιστρέφει σε μορφή json τον χρήστη με username {username}, αν ο χρήστης δεν υπάρχει τα στοιχεία του json έχουν τιμές null.<br><br>


`[PUT] /projects/{id}/archive`

Αλλάζει την κατάσταση του project με id {id} από IN_PROGRESS σε DONE.<br><br>


`[POST] /developers`

Προσθέτει έναν user σε έναν project. Το json που αναμένεται για να επιτύχει το request είναι της μορφής : <br><br>
{"user_id":"${user_id}","project_id":"${project_id}","role":"0/1","accepted":"0"}<br><br>
