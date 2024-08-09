import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import useCreateApi from '@/hooks/use-create-api'
import useGetAllApi from '@/hooks/use-get-all-api'
import SpecificationAttributeConfigs from '@/pages/specificationAttribute/SpecificationAttributeConfigs.ts'
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts'
import { message, Modal } from 'antd'

const useSpecificationAttributeCreateViewModel = (form) => {
    const [groups, setGroups] = useState([])
    const [loadingGroups, setLoadingGroups] = useState(true)
    const [shouldRedirect, setShouldRedirect] = useState(false)
    const navigate = useNavigate()

    const { mutate, isLoading: creating } = useCreateApi(SpecificationAttributeConfigs.resourceUrl)
    const { data, error } = useGetAllApi(
        SpecificationAttributeGroupConfigs.resourceUrl,
        SpecificationAttributeGroupConfigs.resourceKey,
    )

    useEffect(() => {
        if (data) {
            setGroups(data.items)
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
