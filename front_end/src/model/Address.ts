export type AddressRequest = {
    firstName: string
    lastName: string
    districtId: string
    provinceId: string
    wardId: string
    addressName: string
    phoneNumber?: string
    addressTypeId: string
    customerId?: string
}

export type AddressResponse = {
    id: number
    firstName: string
    lastName: string
    districtId: string
    provinceId: string
    wardId: string
    addressName: string
    phoneNumber: string
    addressTypeId: string
    customerId?: string
}
export type Province = {
    code: string
    nameEn: string
}
export type District = {
    code: string
    nameEn: string
    provinceCode: string
}
export type Ward = {
    code: string
    nameEn: string
    districtCode: string
}
