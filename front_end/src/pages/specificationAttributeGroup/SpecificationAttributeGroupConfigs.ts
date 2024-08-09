import { Configs } from '@/types/Configs'
import ManagerPath from '@/constants/ManagerPath .ts'
import ResourceURL from '@/constants/ResourceURL .ts'

export default class SpecificationAttributeGroupConfigs extends Configs {
    static readonly managerPath = ManagerPath.SPECIFICATION_ATTRIBUTE_GROUP
    static readonly resourceUrl = ResourceURL.SPECIFICATION_ATTRIBUTE_GROUP
    static readonly resourceGetUngroupedAttributes = ResourceURL.USE_GET_UNGROUPED_ATTRIBUTES
    static readonly resourceGetUngroupedAttributesKey = 'resourceGetUngroupedAttributes'
    static readonly resourceKey = 'specificationAttributeGroups'
    static readonly createTitle = 'Create Specification Attribute Group'
    static readonly updateTitle = 'Update Specification Attribute Group'
    static readonly searchTitle = 'Search'
}
