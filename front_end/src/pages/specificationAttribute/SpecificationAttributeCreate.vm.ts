import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import useCreateApi from '@/hooks/use-create-api'
import SpecificationAttributeConfigs from '@/pages/specificationAttribute/SpecificationAttributeConfigs.ts'
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts'
import { FormInstance, message, Modal } from 'antd'
import useGetApi from '@/hooks/use-get-api'
import { SpecificationAttributeGroupNameResponse } from '@/model/SpecificationAttributeGroup'

const useSpecificationAttributeCreateViewModel = (form: FormInstance) => {
    const [groups, setGroups] = useState<SpecificationAttributeGroupNameResponse[]>([])
    const [loadingGroups, setLoadingGroups] = useState(true)
    const [shouldRedirect, setShouldRedirect] = useState(false)
    const navigate = useNavigate()

    const { mutate, isSuccess: creating } = useCreateApi(SpecificationAttributeConfigs.resourceUrl)
    const { data, error } = useGetApi<SpecificationAttributeGroupNameResponse[]>(
        `${SpecificationAttributeGroupConfigs.resourceUrl}/list-name`,
        SpecificationAttributeGroupConfigs.resourceKey,
    )

    useEffect(() => {
        if (data) {
            setGroups(data) // Set array directly
            setLoadingGroups(false)
        }
        if (error) {
            message.error(`Error loading groups: ${error.message}`)
            setLoadingGroups(false)
        }
    }, [data, error])

    const handleSubmit = (values: { name: string; group: string; displayOrder: string }) => {
        const requestBody = {
            name: values.name,
            specificationAttributeGroupId: values.group,
            displayOrder: Number(values.displayOrder),
        }

        mutate(requestBody, {
            onSuccess: () => {
                message.success('Created successfully')
                if (shouldRedirect) {
                    navigate('/admin/specification-attributes')
                } else {
                    form.resetFields()
                }
            },
            onError: (error) => {
                message.error(`Error creating attribute: ${error.message}`)
            },
        })
    }

    const showSaveConfirm = () => {
        Modal.confirm({
            title: 'Are you sure you want to save?',
            content: 'Please confirm if you want to save the changes.',
            onOk: () => {
                setShouldRedirect(true)
                form.submit()
            },
        })
    }

    return {
        groups,
        loadingGroups,
        handleSubmit,
        showSaveConfirm,
        creating,
        setShouldRedirect,
    }
}

export default useSpecificationAttributeCreateViewModel
