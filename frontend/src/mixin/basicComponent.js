import dictionaryUtil from "../util/dictionaryUtil";
import shared from "../util/shared";
import {mapState} from "vuex";

export default {
    computed: {
        ...mapState({
            basicUrl: state => state.dictionary.basicUrl,
            authorization: state => state.dictionary.authorization,
            editMode: state => state.dictionary.editMode,
            langs: state => state.dictionary.langs,
            lang: state => state.dictionary.lang,
            loadingState: state => state.dictionary.loadingState
        })
    },

    methods: {
        translate(text) {
            return dictionaryUtil.translate(text);
        },

        isEmpty(value) {
            return shared.isEmpty(value);
        },

        isLoading() {
            return shared.isLoading(this.loadingState);
        }
    }
}