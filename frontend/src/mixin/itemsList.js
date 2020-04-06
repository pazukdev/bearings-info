import shared from "../util/shared";
import {mapState} from "vuex";

export default {
    props: {
        item: Boolean,
        editableComments: Boolean,
        sorted: Boolean
    },

    computed: {
        ...mapState({
            itemView: state => state.dictionary.itemView
        })
    },

    methods: {
        getItems() {
            return this.itemView.children;
        },

        getDeletedItems() {
            return this.itemView.deletedChildren;
        },

        removeItem(item) {
            shared.removeFromArray(item, this.itemView.children);
            this.itemView.idsToRemove.push(item.itemId);
            this.itemView.deletedChildren.push(item);
        },

        restore(item) {
            this.getItems().push(item);
            shared.removeFromArray(item.itemId, this.itemView.idsToRemove);
            shared.removeFromArray(item, this.getDeletedItems());
        },

        isEdit() {
            return this.editableComments && this.editMode;
        }
    }
}