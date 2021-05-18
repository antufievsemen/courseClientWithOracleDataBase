var journalApi = Vue.resource('/library/journal{/id}')
var booksApi = Vue.resource('/library/books{/id}');
var clientApi = Vue.resource('/library/clients{/id}');

function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

Vue.component('record-form', {
    props: ['records', 'books', 'clients'],
    data: function () {
        return {
            id: '',
            record_bookId: '',
            record_clientId: '',
            selected_book: '',
            selected_client: ''
        }
    },
    template:
        '<div>' +
        '<p><b>Идентификатор книги:</b>' +
        '<select v-model="selected_book">' +
        '<option v-for="book in books" :key="book.id" v-bind:value="book.id">' +
        '{{ book.name }}' +
        '</option>' +
        '</select>' +
        // '<input type="text" placeholder="write id book" v-model="record_bookId"/>' +
        '</p>' +
        '<p><b>Идентификатор клиента:</b>' +
        '<select v-model="selected_client">' +
        '<option v-for="client in clients" :key="client.id" v-bind:value="client.id">' +
        '{{ client.passportNumber }}' +
        '</option>' +
        '</select>' +
        // '<input type="number" placeholder="write id client" v-model="record_clientId"/>' +
        '</p>' +
        '<input type="button" value="Save" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var record = {
                book: {
                    id: this.selected_book
                },
                client: {
                    id: this.selected_client
                }
            }
            journalApi.save({}, record).then(result =>
                result.json().then(data => {
                    this.records.push(data);
                    this.record_clientId = '';
                    this.record_bookId = '';
                })
            )
        }
    }
});

Vue.component('record-row', {
    props: ['records', 'record'],
    data: function () {
        return {
            client_botNull: true,
            book_notNull: true,
            book_notReturn: false
        }
    },
    template:
        '<div> ' +
        '<i>' +
        'ID: ({{ record.id }}), ' +
        '</i> ' +
        '<div v-if="book_notNull"> ' +
        'Book name: {{ record.book.name }}' +
        '</div>' +
        '<div v-if="client_botNull">' +
        'Client First Name: {{ record.client.firstName }}, ' +
        'Client Last Name: {{ record.client.lastName }}, ' +
        'Client Father Name: {{ record.client.fatherName }} ,' +
        'Client Passport seria: {{ record.client.passportSeria }} ,' +
        'Client Passport Number: {{ record.client.passportNumber }} ,' +
        '</div>' +
        'Date Begin: {{ record.dateBeg }} ,' +
        'Date End: {{ record.dateEnd }} ,' +
        'Date Return: {{ record.dateReturn }} .' +
        '<div v-if="book_notReturn">' +
        '<input type="button" value="Return" @click="edit"/>' +
        '</div>' +
        '</div>',
    created: function () {
        if (this.record.client == null) {
            this.client_botNull = false;
        }
        if (this.record.book == null) {
            this.book_notNull = false;
        }
        if (this.record.dateReturn == null) {
            this.book_notReturn = true;
        }
    },
    methods: {
        edit: function () {
            var record = this.record;
            journalApi.update({id: record.id}, record).then(result =>
                result.json().then(data => {
                        this.book_notReturn = false;
                    }
                )
            )
        }
    }
});

Vue.component('records-list', {
    props: ['records', 'clients', 'books'],
    template:
        '<div>' +
        '<record-form :records="records" :books="books" :clients="clients"/>' +
        '<record-row v-for="record in records" :key="record.id" :record="record" :records="records"/>' +
        '</div>',
    created: function () {
        journalApi.get().then(result =>
            result.json().then(data =>
                data.forEach(record => this.records.push(record))
            )
        );
        booksApi.get().then(result =>
            result.json().then(data =>
                data.forEach(book => this.books.push(book))
            )
        );
        clientApi.get().then(result =>
            result.json().then(data =>
                data.forEach(client => this.clients.push(client))
            )
        );
    }
});


var recordList = new Vue({
    el: '#journal_main',
    template: '<records-list :records="records" :books="books" :clients="clients"/>',
    data: {
        records: [],
        books: [],
        clients: []
    }
});