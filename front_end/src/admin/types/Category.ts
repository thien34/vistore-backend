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
    categoryParent: CategoryParentResponse | null
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
    categoryParentId: number
    pictureId: number
    showOnHomePage: boolean
    includeInTopMenu: boolean
    pageSize: number
    published: boolean
    displayOrder: number
    priceRangeFiltering: boolean
}

export type CategoryResponseWithPage = {
    status: number
    message: string
    data: {
        page: number
        size: number
        total: number
        items: CategoriesResponse[]
    }
}

export type CategoryFilter = {
    name?: string
    pageNo?: number
    pageSize?: number
    published?: boolean
}

export type CategoryResponseBasic = {
    status: number
    message: string
}
