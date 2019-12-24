<template>
    <div>
        <table class="equal-columns-table">
            <tbody>
                <tr>
                    <td style="text-align: left">
                        {{"v 2.0"}}
                    </td>
                    <td/>
                    <td style="text-align: right">
                        <LanguageSelect/>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import LanguageSelect from "../element/select/LanguageSelect";

    export default {
        name: "LangMenu",
        components: {LanguageSelect}
    }
</script>

<style scoped>

</style>