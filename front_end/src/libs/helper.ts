const normalUrl = (url: string) => {
    return url.startsWith('/') ? url : `/${url}`
}

export { normalUrl }
