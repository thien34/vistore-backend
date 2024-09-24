import { useEffect, useState } from 'react'
import { Form, Modal } from 'antd'
import { useNavigate, useParams } from 'react-router-dom'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import useUpdateApi from '@/hooks/use-update-api'
import { CustomerRoleRequest } from '@/model/CustomerRole'
import CustomerRoleConfigs from './CustomerRoleConfigs'
import ManagerPath from '@/constants/ManagerPath'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api'

function useCustomerRoleUpdateViewModel() {
    const { id } = useParams<{ id: string }>()
    const [form] = Form.useForm<CustomerRoleRequest>()
    const navigate = useNavigate()
    const [isSystemRole, setIsSystemRole] = useState(false)
    const { mutate: updateCustomerRole } = useUpdateApi<CustomerRoleRequest>(
        CustomerRoleConfigs.resourceUrl,
        CustomerRoleConfigs.resourceKey,
        Number(id),
    )

    const { data: initialValues, isLoading } = useGetByIdApi<CustomerRoleRequest>(
        CustomerRoleConfigs.resourceUrl,
        CustomerRoleConfigs.resourceKey,
        Number(id),
    )

    const handleValuesChange = (changedValues: Partial<CustomerRoleRequest>) => {
        if (changedValues.name) {
            const systemRoles = ['Administrators', 'Guests', 'Customers', 'Employees']
            setIsSystemRole(systemRoles.includes(changedValues.name))
        }
    }

    useEffect(() => {
        if (initialValues) {
            form.setFieldsValue(initialValues)
            setIsSystemRole(['Administrators', 'Guests', 'Customers', 'Employees'].includes(initialValues.name))
        }
    }, [initialValues, form])

    const onFinish = (values: CustomerRoleRequest, redirect: boolean) => {
        updateCustomerRole(values, {
            onSuccess: () => {
                if (redirect) {
                    navigate(ManagerPath.CUSTOMER_ROLE)
                }
            },
        })
    }
    const handleSaveAndContinue = (values: CustomerRoleRequest) => {
        onFinish(values, false)
    }

    const handleSave = (values: CustomerRoleRequest) => {
        onFinish(values, true)
    }
    const deleteProductApi = useDeleteByIdApi<number>(CustomerRoleConfigs.resourceUrl, CustomerRoleConfigs.resourceKey)
    const handleDeleteCustomerRole = () => {
        if (Number(id)) {
            Modal.confirm({
                title: 'Are you sure you want to delete this item?',
                content: 'This action cannot be undone.',
                okText: 'Delete',
                okType: 'danger',
                cancelText: 'Cancel',
                onOk: () => {
                    if (Number(id)) {
                        deleteProductApi.mutate(Number(id), {
                            onSuccess: () => {
                                navigate(ManagerPath.CUSTOMER_ROLE)
                            },
                        })
                    }
                },
            })
        }
    }

    return {
        form,
        isLoading,
        onFinish,
        handleValuesChange,
        isSystemRole,
        handleSaveAndContinue,
        handleSave,
        handleDeleteCustomerRole,
    }
}

export default useCustomerRoleUpdateViewModel
