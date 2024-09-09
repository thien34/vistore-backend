import { SpecificationAttributeOptionResponse } from '@/model/SpecificationAttributeOption.ts'

export type SpecificationAttributeRequest = {
    id?: number
    name: string
    specificationAttributeGroupId?: number | null
    displayOrder: number
    listOptions: Array<SpecificationAttributeOptionResponse>
}

export type SpecificationAttributeResponse = {
    id: number
    name: string
    specificationAttributeGroupId: number | null
    specificationAttributeGroupName?: string | null
    displayOrder: number
    listOptions: Array<SpecificationAttributeOptionResponse>
}
