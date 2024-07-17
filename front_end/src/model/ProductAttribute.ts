import { PredefinedProductAttributeValueResponse } from '@/model/PredefinedProductAttributeValue.ts'

export type ProductAttributeRequest = {
    id: number
    name: string
    description: string
    values: PredefinedProductAttributeValueResponse
}

export type ProductAttributeResponse = {
    values: Array<PredefinedProductAttributeValueResponse>
    id: number
    name: string
    description: string
}
