module.exports = {
    publicPath: '/',
    pwa: {
        name: 'Old Vehicles',
        appleMobileWebAppCapable: "yes",
        themeColor: "#617D89",
        manifestOptions: {
            display: "standalone",
            background_color: "#212121"
        },
        workboxOptions: {
            navigateFallback: '/index.html',
            runtimeCaching: [{
                urlPattern: new RegExp('^https'),
                handler: 'NetworkFirst',
                options: {
                    networkTimeoutSeconds: 2,
                    cacheName: 'api-cache',
                    cacheableResponse: {
                        statuses: [200],
                    },
                },
            }]
        }
    }
};