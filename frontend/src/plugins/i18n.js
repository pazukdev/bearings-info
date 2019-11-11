import Vue from 'vue';
import VueI18n from "vue-i18n";

Vue.use(VueI18n);

export const i18n = new VueI18n({
    locale: "en",
    fallbackLocale: "en",
    messages: {
        en: {
            language: "English",
            continueAsGuest: "Continue as guest",
            back: "Back",
            signUp: "Sign up",
            logout: "Logout",
            loginButton: "Login",
            login: "Login",
            password: "Password",
            repeatPassword: "Repeat password",
            please: "Please",
            or: "or",
            incorrectLoginOrPassword: "Incorrect login or password !",
            wishlist: "Wishlist",
            itemsPcs: "items",
            rating: "Rating",
            youAreAdmin: "You are admin",
            youAreGuest: "You are guest",
            addToWishList: "Add to Wish List",
            itemInWishList: "Item in Wish List",
            loading: "Loading",
            edit: "Edit",
            save: "Save",
            cancel: "Cancel",
            create: "Create",
            createdBy: "Created by",
            category: "Category",
            name: "Name",
            createNewItem: "Create new item",
            additionalMenu: "Menu",
            itemsManagement: "Items management",
            users: "Users"
        },
        ru: {
            language: "Русский язык",
            continueAsGuest: "Продолжить как гость",
            back: "Назад",
            signUp: "Зарегистрироваться",
            logout: "Выйти",
            loginButton: "Войти",
            login: "Логин",
            password: "Пароль",
            repeatPassword: "Повторить пароль",
            please: "Пожалуйста",
            or: "или",
            incorrectLoginOrPassword: "Неправильный логин или пароль !",
            wishlist: "Список покупок",
            itemsPcs: "предмет(ов)",
            rating: "Рейтинг",
            youAreAdmin: "Вы админ",
            youAreGuest: "Вы гость",
            addToWishList: "Добавить в список",
            itemInWishList: "Предмет в списке",
            loading: "Загрузка",
            edit: "Редактировать",
            save: "Сохранить",
            cancel: "Отменить",
            create: "Создать",
            createdBy: "Добавлен пользователем",
            category: "Категория",
            name: "Наименование",
            createNewItem: "Добавить новый объект",
            additionalMenu: "Меню",
            itemsManagement: "Управление деталями",
            users: "Пользователи"
        }
    }
});