import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import useCreateApi from '@/hooks/use-create-api'
import SpecificationAttributeConfigs from '@/pages/specificationAttribute/SpecificationAttributeConfigs.ts'
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts'
import { Form } from 'antd'
import useGetApi from '@/hooks/use-get-api'
import { SpecificationAttributeGroupNameResponse } from '@/model/SpecificationAttributeGroup'

const useSpecificationAttributeCreateViewModel = () => {
    const [form] = Form.useForm()
    const [groups, setGroups] = useState<SpecificationAttributeGroupNameResponse[]>([])
    const navigate = useNavigate()

    const { mutate } = useCreateApi(SpecificationAttributeConfigs.resourceUrl)
    const { data } = useGetApi<SpecificationAttributeGroupNameResponse[]>(
        `${SpecificationAttributeGroupConfigs.resourceUrl}/list-name`,
        SpecificationAttributeGroupConfigs.resourceKey,
    )

    const layout = {
        labelCol: { span: 8 },
        wrapperCol: { span: 13 },
    }

    useEffect(() => {
        if (data) {
            setGroups(data)
        }
    }, [data])

    const handleSubmit = (values: { name: string; group: string; displayOrder: string }) => {
        const requestBody = {
            name: values.name,
            specificationAttributeGroupId: values.group,
            displayOrder: Number(values.displayOrder),
        }

        mutate(requestBody, {
            onSuccess: () => {
                navigate(-1)
            },
        })
    }

    const handleSave = () => {
        form.submit()
    }

    return {
        groups,
        handleSubmit,
        handleSave,
        layout,
        form,
    }
}

export default useSpecificationAttributeCreateViewModel
