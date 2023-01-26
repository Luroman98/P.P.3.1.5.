const ADMIN = 'http://localhost:8080/restAdmin'

function getRolesId(x) {
    fetch(ADMIN + '/roles').then(
        res => {
            $(`#roles${x}`).empty()
            res.json().then(
                data => {
                    data.forEach(role => {
                        $(`#roles${x}`)
                            .append($('<option>')
                                .val(role.id).text(role.name))
                    })
                }
            )
        }
    )
}
function addRoles(roleList) {
    let array = []
    let options = document.querySelector(roleList).options
    for (let i = 0; i < options.length; i++) {
        if (options[i].selected) {
            jQuery.ajax({
                url: 'http://localhost:8080/restAdmin/role/' + options[i].value,
                async: false,
                type: 'GET',
                dataType: 'json',
                success: function(resp) {
                    array.push(resp)
                }
            })
            console.log(array)
        }
    }
    return array
}

function User(x) {
    if (x !== 'C') {
        this.id = $(`#id${x}`).val()
    }
    this.username = $(`#username${x}`).val()
    this.password = $(`#password${x}`).val()
    this.lastName = $(`#lastName${x}`).val()
    this.age = $(`#age${x}`).val()
    this.email = $(`#email${x}`).val()
    this.roles = addRoles(`#roles${x}`)
}

getUser()
tableUsers()
getRolesId('C')


function tableUsers() {
    fetch(ADMIN).then(
        res => {
            res.json().then(
                data => {
                    if (data.length > 0) {
                        let temp = ''
                        console.log(data)
                        data.forEach(u => {
                            temp += '<tr>'
                            temp += '<td>' + u.id
                            temp += '<td>' + u.username
                            temp += '<td>' + u.lastName
                            temp += '<td>' + u.age
                            temp += '<td>' + u.email
                            let roles = ''
                            u.roles.forEach(x => roles += x.name + ' ')
                            temp += '<td>' + roles
                            temp += '<td>' + `<a type="button" class="btn btn-sm btn-primary"` +
                                `onclick="form(${u.id}, 'U', 'update')">Edit</a>`
                            temp += '<td>' + `<a type="button" class="btn btn-danger"` +
                                `onclick="form(${u.id}, 'D', 'delete')">Delete</a>`
                        })
                        document.getElementById('tableUsers').innerHTML = temp
                    }
                }
            )
        }
    )
}

function getUser() {
    fetch(ADMIN + "/user").then(
        res => {
            res.json().then(
                data => {
                    console.log(data)
                    let temp = ''
                    temp += '<tr>'
                    temp += '<td>' + data.id
                    temp += '<td>' + data.username
                    temp += '<td>' + data.lastName
                    temp += '<td>' + data.age
                    temp += '<td>' + data.email
                    let roles = ''
                    data.roles.forEach(x => roles += x.name + ' ')
                    temp += '<td>' + roles

                    document.getElementById('aboutUser').innerHTML = temp

                    document.querySelector('#emailUser').textContent = data.email
                    let roleP = ''
                    data.roles.forEach(x => roleP += x.name + ' ')
                    document.querySelector('#roleUser').textContent = roleP
                }
            )
        }
    )
}
function createUser() {
    fetch(ADMIN,
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json; charset=utf-8'
            },
            method: "POST",
            body: JSON.stringify(new User('C'))
        }).then(() => {
        tableUsers()
    })
}

function form(id, x, action) {
    fetch(ADMIN + '/' + id).then(
        res => {
            res.json().then(
                data => {
                    console.log(action)
                    $(`#id${x}`).val(data.id)
                    $(`#username${x}`).val(data.username)
                    $(`#password${x}`).val('')
                    $(`#lastName${x}`).val(data.lastName)
                    $(`#age${x}`).val(data.age)
                    $(`#email${x}`).val(data.email)
                    getRolesId(x)
                    $(`#${action}`).attr('onclick', `${action}User(${data.id})`)
                    $(`#${action}User`).modal('show')
                }
            )
        }
    )
}

function updateUser(id) {
    fetch(ADMIN,
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json; charset=utf-8'
            },
            method: "PUT",
            body: JSON.stringify(new User('U'))
        }).then(() => {
        $('#updateUser').modal("hide")
        tableUsers()
    })
}

function deleteUser(id) {
    fetch(ADMIN + "/" + id,
        {
            method: "DELETE",
        }).then(() => {
        $('#deleteUser').modal("hide")
        tableUsers()
    })
}
//
// fetch(ADMIN)
//     .then(
//         response => response.text() // .json(), .blob(), etc.
//     ).then(
//     text => console.log(text) // Handle here
// );

// const emailUser = document.getElementById('emailUser').innerHTML = 'ooooooooo'
// const roleUser = document.getElementById('roleUser').innerHTML = 'rrrrroooolleee'
//     emailUser.textContent = 'Uuuuuuuuuser'
    // roleUser.textContent = 'rrrrroooolleee'

