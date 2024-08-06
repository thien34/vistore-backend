import { useState } from 'react'

function useSpecificationAttributeManageViewModel() {
    const [attributeType, setAttributeType] = useState('Option')

    const handleAttributeTypeChange = (newType: string) => {
        setAttributeType(newType)
    }
    return { attributeType, handleAttributeTypeChange }
}
export default useSpecificationAttributeManageViewModel
