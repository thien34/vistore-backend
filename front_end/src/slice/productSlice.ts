import { createSlice, PayloadAction } from '@reduxjs/toolkit'

interface ProductState {
    name: string
    shortDescription: string
    fullDescription: string
    sku: string
    published: boolean
    productTags: string[]
    gtin: string
    manufacturePartNumber: string
    showOnHomePage: boolean
    allowCustomerReviews: boolean
    availableStartDateTimeUtc: string | null
    availableEndDateTimeUtc: string | null
    markAsNew: boolean
    markAsNewStartDateTimeUtc: string | null
    markAsNewEndDateTimeUtc: string | null
    adminComment: string
    id: number
    approvedRatingSum: number
    notApprovedRatingSum: number
    approveTotalReviews: number
    notApprovedTotalReviews: number
    isShipEnabled: boolean
    isFreeShipping: boolean
    productAvailabilityRangeId: number
    displayStockAvailability: boolean
    displayStockQuantity: boolean
    minStockQuantity: number
    notReturnable: boolean
    unitPrice: number
    oldPrice: number
    productCost: number
    weight: number
    length: number
    width: number
    height: number
    displayOrder: number
    deleted: boolean
    categoryIds: number[]
    manufacturerIds: number[]
    discountIds: number[]
    minCartQty: number
    maxCartQty: number
    manageInventoryMethodId: number
    errors: Record<string, string>
}

export const initialState: ProductState = {
    name: '',
    shortDescription: '',
    fullDescription: '',
    sku: '',

    published: false,
    productTags: [],
    gtin: '',
    manufacturePartNumber: '',
    showOnHomePage: false,
    allowCustomerReviews: false,
    availableEndDateTimeUtc: null,
    availableStartDateTimeUtc: null,
    markAsNew: false,
    markAsNewStartDateTimeUtc: null,
    markAsNewEndDateTimeUtc: null,
    adminComment: '',
    id: -1,
    approvedRatingSum: 0,
    notApprovedRatingSum: 0,
    approveTotalReviews: 0,
    notApprovedTotalReviews: 0,
    isShipEnabled: false,
    isFreeShipping: false,
    manageInventoryMethodId: 0,
    productAvailabilityRangeId: 0,
    displayStockAvailability: false,
    displayStockQuantity: false,
    minStockQuantity: 0,
    notReturnable: false,
    unitPrice: 0,
    oldPrice: 0,
    productCost: 0,
    weight: 0,
    length: 0,
    width: 0,
    height: 0,
    displayOrder: 0,
    deleted: false,
    categoryIds: [],
    manufacturerIds: [],
    discountIds: [],
    minCartQty: 0,
    maxCartQty: 0,
    errors: {},
}

const productSlice = createSlice({
    name: 'product',
    initialState,
    reducers: {
        setProduct: (state, action: PayloadAction<Partial<ProductState> | null>) => {
            return { ...state, ...action.payload }
        },
        setProductErrors: (state, action: PayloadAction<Record<string, string>>) => {
            state.errors = action.payload
        },
        resetProductState: () => {
            return initialState
        },
    },
})

export const { setProduct, setProductErrors, resetProductState } = productSlice.actions
export default productSlice.reducer
