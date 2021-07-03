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



**Required npm installs:**

npm install --save @fullcalendar/react @fullcalendar/daygrid

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
{"title":string,"description":string,"status":"0","date_finished":null}<br><br>

`[GET] /users/name={username}`

Επιστρέφει σε μορφή json τον χρήστη με username {username}, αν ο χρήστης δεν υπάρχει τα στοιχεία του json έχουν τιμές null.<br><br>


`[PUT] /projects/{id}/archive/{user_id}`

Αλλάζει την κατάσταση του project με id {id} από IN_PROGRESS σε DONE, το οποίο αίτημα κάνει ο user με ${user_id}.<br><br>

`[DELETE] /projects/{id}/delete/{user_id}`

Διαγράφει το project με id {id}, το οποίο αίτημα κάνει ο user με ${user_id}.<br><br>

`[POST] /developers/`

Προσθέτει έναν user σε έναν project. Το json που αναμένεται για να επιτύχει το request είναι της μορφής : <br><br>
{"user_id":"${user_id}","project_id":"${project_id}","role":"0/1","accepted":"0"}<br><br>

`[PUT] /developers/users/{user_id}/projects/{project_id}/accept`

Δέχεται ο developer με user_id {user_id} και project_id {project_id}. Η απάντηση είναι OK σε περίπτωση επιτυχίας και "ERROR" διαφορετικά.<br><br>

`[DELETE] /developers/users/{user_id}/projects/{project_id}`

Διαγράφεται ο developer με {user_id} και {project_id}.<br><br>

`[GET] /developers/users/{user_id}/notifications`

Ο συνολικός αριθμός αιτημάτων που αφορούν τον user με id {user_id} όσον αφορά τα πρότζεκτ στα οποία συμμετέχει. Η απάντηση είναι της μορφής :

[num] , όπου num ένας ακέραιος.<br><br>

`[POST] /projects/epics/create`

Endpoint για τη δημιουργία ενός καινούριου epic. Το json που αναμένεται για το request είναι της εξής μορφής : <br><br>
{"title":"Title","description":"Description","status":0,"project_id":"${project_id}"}<br><br>

`[GET] projects/{project_id}/epics/{id}`

Endpoint για την απόκτηση ενός Epic. Σε περίπτωση λάθους γίνεται Runtime exception, διαφορετικά επιστρέφει σε μορφή json το Epic.<br><br>

`[GET] /projects/{project_id}/epics`

Endpoint για την απόκτηση όλων τωω Epic.<br><br>

`[PUT] projects/{project_id}/epics/{id}/archive/{user_id}`

Endpoint για να γίνει archive το Epic με ${id} που βρίσκεται στο project με ${project_id}, η οποία αίτηση γίνεται από το χρήστη ${user_id}.Η απάντηση είναι ΟΚ αν δεν υπάρχει κάποιο λάθος. <br><br>

`[DELETE] projects/{project_id}/epics/{id}/delete/{user_id}`

Endpoint για να γίνει delete το Epic με ${id} που βρίσκεται στο project με ${project_id}, η οποία αίτηση γίνεται από το χρήστη ${user_id}.<br><br>

`[PUT] /projects/{project_id}/sprints/{id}/archive/{user_id}`

Endpoint για να γίνει archive το Sprint με ${id} που βρίσκεται στο project με ${project_id}, η οποία αίτηση γίνεται από το χρήστη ${user_id}. Αν δεν υπάρξει πρόβλημα στην εκτέλεση επιστρέφεται ένα καινούριου sprint ( με status 3 ) σε μορφή json.<br><br>

`[GET] /projects/{project_id}/sprints`

Endpoint για την απόκτηση όλων των sprint σε ένα project σε μορφή Json.<br><br>

`[GET] /projects/{project_id}/sprints/active`

Endpoint για την απόκτηση όλων των ενεργών sprint (status: 1/2/3) σε ένα project σε μορφή Json.<br><br>

`[DELETE] /projects/{project_id}/sprints/{id}/delete/{user_id}`

Διαγραφή του sprint με ${id} στο project ${project_id} από τον χρήστη ${user_id}. To sprint μπορεί να διαγραφεί μόνο αν είναι ολοκληρωμένο ( status : 0 ).<br><br>

`[GET] /projects/{project_id}/epics/{epic_id}/stories`

Επιστροφή όλων των stories σε ένα epic se μορφή Json.<br><br>

`[GET] /projects/{project_id}/sprints/{sprint_id}/stories`

Επιστροφή όλων των stories σε ένα sprint se μορφή Json.<br><br>

`[POST] /projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/create`

Endpoint για τη δημιουργία ενός καινουρίου story στο project με id ${project_id} που ανήκει στο sprint ${sprint_id} και στο epic ${epic_id}. To json που αναμένεται από το request είναι της μορφής :
{"title":"Title","description":"Description","status":0,"project_id":"${project_id}","epic_id":"${epic_id}","sprint_id}":"${sprint_id}"}.<br><br>

`[PUT] /projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{id}/archive/{user_id}`

Endpoint για να γίνει archive ένα story.<br><br>

`[DELETE] /projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{id}/delete/{user_id}`

Endpoint για τη διαγραφή ενός story.<br><br>

`[GET] /projects/{project_id}/stories/{story_id}/tasks`

Endpoint για την επιστροφή όλων των task σε ένα project.<br><br>

`[GET] /projects/{project_id}/sprints/{sprint_id}/stories/{story_id}/tasks`

Endpoint για την επιστροφή όλων των task σε ένα sprint.<br><br>

`[GET] /projects/{project_id}/epics/{epic_id}/stories/{story_id}/tasks`

Endpoint για την επιστροφή όλων των task σε ένα epic.<br><br>

`[POST] /projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{story_id}/tasks/create`

Endpoint για τη δημιουργία ενός καινουρίου task στο project με id ${project_id} που ανήκει στο sprint ${sprint_id} και στο epic ${epic_id}. To json που αναμένεται από το request είναι της μορφής :
{"title":"Title","description":"Description","status":0,"project_id":"${project_id}","epic_id":"${epic_id}","sprint_id}":"${sprint_id}","story_id":"${story_id}"}.<br><br>

`[GET] /projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{story_id}/tasks/{id}/archive`

Endpoint για να γίνει archive ένα task.<br><br>

`[DELETE] /projects/{project_id}/sprints&epics/{sprint_id}&{epic_id}/stories/{story_id}/tasks/{id}/delete`

Endpoint για να γίνει delete ένα task.<br><br>
