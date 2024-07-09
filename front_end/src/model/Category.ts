export type CategoryParentResponse = {
    id: number
    name: string
    categoryParent: CategoryParentResponse | null
}

export type CategoriesResponse = {
    id: number
    name: string
    categoryParent: CategoryParentResponse | null
    published: boolean
    displayOrder: number
}

export type CategoryResponse = {
    id: number
    name: string
    description: string
    categoryParentId: number | null
    pictureId: number | null
    showOnHomePage: boolean
    includeInTopMenu: boolean
    pageSize: number
    published: boolean
    deleted: boolean
    displayOrder: number
    priceRangeFiltering: boolean
}

export type CategoryRequest = {
    id?: number
    name: string
    description: string
    categoryParentId: number | null
    pictureId: number | null
    showOnHomePage: boolean
    includeInTopMenu: boolean
    pageSize: number
    published: boolean
    displayOrder: number
    priceRangeFiltering: boolean
}
