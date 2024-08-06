import { useNavigate } from 'react-router-dom'
import { Form, message, Modal } from 'antd'
import useCreateApi from '@/hooks/use-create-api.ts'
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts'

const useSpecificationAttributeGroupCreateViewModel = () => {
    const [form] = Form.useForm()
    const navigate = useNavigate()
    const { mutate: createSpecificationAttributeGroup, isLoading } = useCreateApi(
        SpecificationAttributeGroupConfigs.resourceUrl,
    )

    const handleSave = (shouldRedirect: boolean) => {
        form.validateFields()
            .then((values) => {
                createSpecificationAttributeGroup(values, {
                    onSuccess: () => {
                        message.success('Specification attribute group created successfully')
                        form.resetFields()
                        if (shouldRedirect) {
                            navigate('/admin/specification-attribute-groups')
                        }
                    },
                    onError: (error) => {
                        message.error(`Failed to create specification attribute group: ${error.message}`)
                    },
                })
            })
            .catch((errorInfo) => {
                console.log('Validation Failed:', errorInfo)
            })
    }

    const showSaveConfirm = () => {
        Modal.confirm({
            title: 'Are you sure you want to save?',
            content: 'Please confirm if you want to save the changes.',
            onOk: () => handleSave(true),
            onCancel() {
                console.log('Cancel')
            },
        })
    }

    return {
        form,
        isLoading,
        showSaveConfirm,
        handleSave,
    }
}

export default useSpecificationAttributeGroupCreateViewModel
