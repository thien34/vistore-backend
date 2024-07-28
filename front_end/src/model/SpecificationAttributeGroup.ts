import { SpecificationAttributeRequest, SpecificationAttributeResponse } from '@/model/SpecificationAttribute.ts'

export type SpecificationAttributeGroupRequest = {
    name: string
    displayOrder: number
    specificationAttributes?: SpecificationAttributeRequest
}
export type SpecificationAttributeGroupResponse = {
    id: number
    name: string
    displayOrder: number
    specificationAttributes: Array<SpecificationAttributeResponse>
}
