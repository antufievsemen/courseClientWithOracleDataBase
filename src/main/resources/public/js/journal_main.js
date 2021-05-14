var journalApi = Vue.resource('/library/journal{/id}')

function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

Vue.component('record-form', {
    props: ['records'],
    data: function () {
        return {
            id: '',
            record_bookId: '',
            record_clientId: ''
        }
    },
    template:
        '<div>' +
        '<p><b>Идентификатор книги:</b>' +
        '<input type="text" placeholder="write id book" v-model="record_bookId"/>' +
        '</p>' +
        '<p><b>Идентификатор клиента:</b>' +
        '<input type="number" placeholder="write id client" v-model="record_clientId"/>' +
        '</p>' +
        '<input type="button" value="Save" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var record = {
                book: {
                    id: this.record_bookId
                },
                client: {
                    id: this.record_clientId
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
    template:
        '<div> ' +
        '<i>' +
        'ID: ({{ record.id }}), ' +
        '</i> ' +
        '<i>' +
        'Book ID: {{ record.book.id }}, ' +
        '</i> ' +
        'Book name: {{ record.book.name }}' +
        'Client First Name: {{ record.client.firstName }}, ' +
        'Client Last Name: {{ record.client.lastName }}, ' +
        'Client Father Name: {{ record.client.fatherName }} ,' +
        'Client Passport seria: {{ record.client.passportSeria }} ,' +
        'Client Passport Number: {{ record.client.passportNumber }} ,' +
        'Date Begin: {{ record.dateBeg }} ,' +
        'Date End: {{ record.dateEnd }} ,' +
        'Date Return: {{ record.dateReturn }} .' +
        '<input type="button" value="Return" @click="edit"/>' +
        '</div>',
    methods: {
        edit: function () {
            var record = this.record;
            journalApi.update({id: record.id}, record).then(result =>
                result.json().then(data => {
                        var index = getIndex(this.record, data.id);
                        this.records.splice(index, 1, data);

                    }
                )
            )
        }
    }
});

Vue.component('records-list', {
    props: ['records'],
    template:
        '<div>' +
        '<record-form :records="records"/>' +
        '<record-row v-for="record in records" :key="record.id" :record="record" :records="records"/>' +
        '</div>',
    created: function () {
        journalApi.get().then(result =>
            result.json().then(data =>
                data.forEach(record => this.records.push(record))
            )
        )
    }
});


var recordList = new Vue({
    el: '#journal_main',
    template: '<records-list :records="records"/>',
    data: {
        records: []
    }
});