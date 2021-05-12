var typesApi = Vue.resource('/library/types{/id}')

function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

Vue.component('type-form', {
    props: ['types', 'typeAttr'],
    data: function () {
        return {
            id: '',
            type_name: '',
            type_fine: '',
            type_dayCount: ''
        }
    },
    watch: {
        typeAttr: function (newVal, oldVal) {
            this.id = newVal.id;
            this.type_name = newVal.name;
            this.type_fine = newVal.fine;
            this.type_dayCount = newVal.dayCount;
        }
    },
    template:
        '<div>' +
        '<p><b>Название:</b>' +
        '<input type="text" placeholder="write name" v-model="type_name"/>' +
        '</p>' +
        '<p><b>Штраф:</b>' +
        '<input type="number" placeholder="write fine" v-model="type_fine"/>' +
        '</p>' +
        '<p><b>Количество дней до штрафа:</b>' +
        '<input type="number" placeholder="write day count" v-model="type_dayCount"/>' +
        '</p>' +
        '<input type="button" value="Save" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var bookType = {
                name: this.type_name,
                fine: this.type_fine,
                dayCount: this.type_dayCount
            };
            if (this.id) {
                typesApi.update({id: this.id}, bookType).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.types, data.id);
                        this.types.splice(index, 1, data);
                        this.id = '';
                        this.type_name = '';
                        this.type_fine = '';
                        this.type_dayCount = '';
                    })
                )
            } else {
                typesApi.save({}, bookType).then(result =>
                    result.json().then(data => {
                        this.types.push(data);
                        this.name = '';
                        this.fine = '';
                        this.dayCount = '';
                    })
                )
            }
        }
    }
});

Vue.component('type-row', {
    props: ['type', 'editType', 'types'],
    template:
        '<div> ' +
        '<i>' +
        'ID: ({{ type.id }}), ' +
        '</i> ' +
        'Name: {{ type.name }}, ' +
        'Count: {{ type.count }}, ' +
        'Fine: {{ type.fine }} ,' +
        'Day Count: {{ type.dayCount }} ' +
        '<span style="position: absolute; right: 0;">' +
        '<input type="button" value="Edit" @click="edit" />' +
        '<input type="button" value="Delete" @click="del" />' +
        '</span>' +
        '</div>',
    methods: {
        edit: function () {
            this.editType(this.type);
        },
        del: function () {
            typesApi.remove({id: this.type.id}).then(result => {
                if (result.ok) {
                    this.types.splice(this.types.indexOf(this.type), 1)
                }
            })
        }
    }
});

Vue.component('types-list', {
    props: ['types'],
    data: function () {
        return {
            type: null
        }
    },
    template:
        '<div style="position: relative; width: 400px;">' +
        '<type-form :types="types" :typeAttr="type"/>' +
        '<type-row v-for="type in types" :key="type.id" :type="type" :editType="editType" :types="types"/>' +
        '</div>',
    created: function () {
        typesApi.get().then(result =>
            result.json().then(data =>
                data.forEach(type => this.types.push(type))
            )
        )
    },
    methods: {
        editType: function (type) {
            this.type = type;
        }
    }
});


var typeList = new Vue({
    el: '#types_main',
    template: '<types-list :types="types"/>',
    data: {
        types: []
    }
});