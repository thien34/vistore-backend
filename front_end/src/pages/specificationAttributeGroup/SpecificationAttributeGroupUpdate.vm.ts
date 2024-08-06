import { useNavigate, useParams } from 'react-router-dom'
import useGetByIdApi from '@/hooks/use-get-by-id-api.ts'
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts'
import { Form, message, Modal } from 'antd'
import { useEffect, useState } from 'react'
import useUpdateApi from '@/hooks/use-update-api.ts'
import { SpecificationAttributeResponse } from '@/model/SpecificationAttribute.ts'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api.ts'

const useSpecificationAttributeGroupUpdateViewModel = () => {
    const { id } = useParams<{ id: string }>()
    const { data, isLoading } = useGetByIdApi(
        SpecificationAttributeGroupConfigs.resourceUrl,
        SpecificationAttributeGroupConfigs.resourceKey,
        parseInt(id, 10),
    )

    const [form] = Form.useForm()
    const navigate = useNavigate()

    useEffect(() => {
        if (data) {
            form.setFieldsValue({
                name: data.name,
                displayOrder: data.displayOrder,
            })
        }
    }, [data, form])

    const [isSaveAndContinue, setIsSaveAndContinue] = useState(false)

    const updateApi = useUpdateApi<SpecificationAttributeResponse>(
        SpecificationAttributeGroupConfigs.resourceUrl,
        SpecificationAttributeGroupConfigs.resourceKey,
        parseInt(id, 10),
    )

    const deleteApi = useDeleteByIdApi(
        SpecificationAttributeGroupConfigs.resourceUrl,
        SpecificationAttributeGroupConfigs.resourceKey,
    )

    const handleDelete = () => {
        Modal.confirm({
            title: 'Are you sure you want to delete this item?',
            content: 'This action cannot be undone.',
            okText: 'Delete',
            okType: 'danger',
            cancelText: 'Cancel',
            onOk: () => {
                deleteApi.mutate(parseInt(id, 10), {
                    onSuccess: () => {
                        console.log('Deleted successfully!')
                        navigate('/admin/specification-attribute-groups')
                    },
                    onError: (error) => {
                        console.error('Deletion failed:', error)
                        message.error('Deletion failed!')
                    },
                })
            },
        })
    }

    const onFinish = (values: SpecificationAttributeResponse) => {
        updateApi.mutate(values, {
            onSuccess: () => {
                console.log('Update successful!')
                if (isSaveAndContinue) {
                    message.success('Saved successfully. You can continue editing.')
                    form.resetFields()
                } else {
                    message.success('Saved successfully.')
                    navigate('/admin/specification-attribute-groups')
                }
            },
            onError: (error) => {
                console.error('Update failed:', error)
                message.error('Update failed!')
            },
        })
    }

    const onFinishFailed = (errorInfo: never) => {
        console.log('Failed:', errorInfo)
    }

    return {
        form,
        isLoading,
        handleDelete,
        onFinish,
        onFinishFailed,
        isSaveAndContinue,
        setIsSaveAndContinue,
        navigate,
    }
}

export default useSpecificationAttributeGroupUpdateViewModel
