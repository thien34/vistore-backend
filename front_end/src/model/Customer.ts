export type CustomerResponse = {
    id: number
    email: string
    firstName: string
    lastName: string
    active: boolean
    customerRoles: number[]
}
export type CustomerFullRequest = {
    customerGuid: string
    username: string
    password: string
    email: string
    firstName: string
    lastName: string
    gender: string
    phone: string
    dateOfBirth: string
    hasShoppingCartItems: boolean
    requireReLogin: boolean
    failedLoginAttempts: number
    cannotLoginUntilDateUtc: string
    active: boolean
    deleted: boolean
    lastLoginDateUtc: string
    lastActivityDateUtc: string
    customerRoles: number[]
}
export type CustomerFullResponse = {
    customerGuid: string
    username: string
    email: string
    firstName: string
    lastName: string
    gender: string
    phone: string
    dateOfBirth: string
    hasShoppingCartItems: boolean
    requireReLogin: boolean
    failedLoginAttempts: number
    cannotLoginUntilDateUtc: string
    active: boolean
    deleted: boolean
    lastLoginDateUtc: string
    lastActivityDateUtc: string
    customerRoles: number[]
}
