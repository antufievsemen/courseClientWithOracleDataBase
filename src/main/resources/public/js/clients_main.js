var clientApi = Vue.resource('/library/clients{/id}');

function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

Vue.component('client-form', {
    props: ['clients', 'clientAttr'],
    data: function () {
        return {
            id: '',
            client_firstName: '',
            client_lastName: '',
            client_fatherName: '',
            client_passportSeria: '',
            client_passportNumber: ''
        }
    },
    watch: {
        clientAttr: function (newVal, oldVal) {
            this.id = newVal.id;
            this.client_firstName = newVal.firstName;
            this.client_lastName = newVal.lastName;
            this.client_fatherName = newVal.fatherName;
            this.client_passportSeria = newVal.passportSeria;
            this.client_passportNumber = newVal.passportNumber;
        }
    },
    template:
        '<div>' +
        '<p><b>Имя клиента:</b>' +
        '<input type="text" placeholder="write first name" v-model="client_firstName"/>' +
        '</p>' +
        '<p><b>Фамилия клиента:</b>' +
        '<input type="text" placeholder="write lastname" v-model="client_lastName"/>' +
        '</p>' +
        '<p><b>Отчество клиента:</b>' +
        '<input type="text" placeholder="write father name" v-model="client_fatherName"/>' +
        '</p>' +
        '<p><b>Пасспорт серия:</b>' +
        '<input type="text" placeholder="write passport seria" v-model="client_passportSeria"/>' +
        '</p>' +
        '<p><b>Паспорт номер:</b>' +
        '<input type="text" placeholder="write passport number" v-model="client_passportNumber"/>' +
        '</p>' +
        '<input type="button" value="Save" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var client = {
                firstName: this.client_firstName,
                lastName: this.client_lastName,
                fatherName: this.client_fatherName,
                passportSeria: this.client_passportSeria,
                passportNumber: this.client_passportNumber
            };
            if (this.id) {
                clientApi.update({id: this.id}, client).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.clients, data.id);
                        this.clients.splice(index, 1, data);
                        this.id = '';
                        this.client_firstName = '';
                        this.client_lastName = '';
                        this.client_fatherName = '';
                        this.client_passportSeria = '';
                        this.client_passportNumber = '';
                    })
                )
            } else {
                clientApi.save({}, client).then(result =>
                    result.json().then(data => {
                            this.clients.push(data);
                            this.client_firstName = '';
                            this.client_lastName = '';
                            this.client_fatherName = '';
                            this.client_passportSeria = '';
                            this.client_passportNumber = '';
                        }
                    )
                );
            }


        }
    }
})
Vue.component('client-row', {
    props: ['client', 'clients', 'editClient'],
    template:
        '<div> ' +
        '<i>' +
        'ID: ({{ client.id }}), ' +
        '</i> ' +
        'First Name: {{ client.firstName }}, ' +
        'Last Name: {{ client.lastName }}, ' +
        'Father Name: {{ client.fatherName }} ,' +
        'Passport seria: {{ client.passportSeria }} ,' +
        'Passport Number: {{ client.passportNumber }} .' +
        '<span style="position: absolute; right: 0;">' +
        '<input type="button" value="Edit" @click="edit" />' +
        '<input type="button" value="Delete" @click="del" />' +
        '</span>' +
        '</div>',
    methods: {
        edit: function () {
            this.editClient(this.client);
        },
        del: function () {
            clientApi.remove({id: this.client.id}).then(result => {
                if (result.ok) {
                    this.clients.splice(this.clients.indexOf(this.client), 1)
                }
            })
        }
    }
});

Vue.component('clients-list', {
    props: ['clients'],
    data: function () {
        return {
            client: null
        }
    },
    template:
        '<div>' +
        '<client-form :clients="clients" :clientAttr="client"/>' +
        '<client-row v-for="client in clients" :key="client.id" :client="client" :clients="clients" :editClient="editClient"/>' +
        '</div>',
    created: function () {
        clientApi.get().then(result =>
            result.json().then(data =>
                data.forEach(client => this.clients.push(client))
            )
        )
    },
    methods: {
        editClient: function (client) {
            this.client = client;
        }
    }
});


var clientList = new Vue({
    el: '#clients_main',
    template: '<clients-list :clients="clients"/>',
    data: {
        clients: []
    }
});