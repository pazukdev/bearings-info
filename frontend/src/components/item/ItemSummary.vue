<template>
    <div>
        <table>
            <tbody>
            <tr><td><hr></td></tr>
            <tr>
                <td>
                    <details>
                        <summary>{{"All parts"}}</summary>
                        <CountedItemList :summary-view="true" :sorted="true"/>
                    </details>
                </td>
            </tr>
            <tr><td><hr></td></tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import CountedItemList from "../list/CountedItemList";

    export default {
        name: "ItemSummary",
        components: {CountedItemList}
    }
</script>

<style scoped>
    hr {
        margin: 0;
    }
</style>