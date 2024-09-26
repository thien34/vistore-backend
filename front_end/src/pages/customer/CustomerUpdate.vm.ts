import { useNavigate, useParams } from 'react-router-dom'
import CustomerConfigs from './CustomerConfigs'
import useUpdateApi from '@/hooks/use-update-api'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import useGetApi from '@/hooks/use-get-api'
import { CustomerFullRequest, CustomerFullResponse } from '@/model/Customer'
import { CustomerRoleResponse } from '@/model/CustomerRole'
import dayjs from 'dayjs'
import ManagerPath from '@/constants/ManagerPath'
import { useEffect, useState } from 'react'
import { Form, Modal, RadioChangeEvent } from 'antd'
import CustomerRoleConfigs from '../customer-roles/CustomerRoleConfigs'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api'

function useCustomerUpdateViewModel() {
    const { id } = useParams<{ id: string }>()
    const [form] = Form.useForm()
    const navigate = useNavigate()
    const [error, setError] = useState<string | null>(null)
    const [value, setValue] = useState('MALE')
    const [customerRoles, setCustomerRoles] = useState<CustomerRoleResponse[]>([])

    const { data, isSuccess } = useGetByIdApi<CustomerFullResponse>(
        CustomerConfigs.resourceUrl,
        CustomerConfigs.resourceKey,
        Number(id),
    )
    const { data: rolesData } = useGetApi<CustomerRoleResponse[]>(
        `${CustomerRoleConfigs.resourceUrl}/list-name`,
        CustomerRoleConfigs.resourceKey,
    )
    const { mutate: updateApi } = useUpdateApi<CustomerFullRequest>(
        CustomerConfigs.resourceUrl,
        CustomerConfigs.resourceKey,
        Number(id),
    )
    const { mutate: deleteCustomer } = useDeleteByIdApi<number>(
        CustomerConfigs.resourceUrl,
        CustomerConfigs.resourceKey,
    )
    useEffect(() => {
        document.title = 'Update a new customer - Vítore'
    }, [])

    useEffect(() => {
        if (isSuccess && data) {
            form.setFieldsValue({
                ...data,
                dateOfBirth: data.dateOfBirth ? dayjs(data.dateOfBirth) : null,
                customerRoles: data.customerRoles || [],
            })
        } else if (!isSuccess) {
            setError('Failed to fetch customer data.')
        }

        if (rolesData) {
            setCustomerRoles(rolesData)
        }
    }, [isSuccess, data, form, rolesData])
    const onFinish = (values: CustomerFullRequest) => {
        const customerRequest: CustomerFullRequest = {
            ...values,
        }
        updateApi(customerRequest, {
            onSuccess: () => {
                navigate(ManagerPath.CUSTOMER)
            },
        })
    }
    const handleDelete = () => {
        if (Number(id)) {
            Modal.confirm({
                title: 'Are you sure you want to delete this item?',
                content: 'This action cannot be undone.',
                okText: 'Delete',
                okType: 'danger',
                cancelText: 'Cancel',
                onOk: () => {
                    deleteCustomer(Number(id), {
                        onSuccess: () => {
                            navigate(ManagerPath.CUSTOMER)
                        },
                    })
                },
            })
        }
    }
    const onChange = (e: RadioChangeEvent) => {
        setValue(e.target.value)
    }

    return { form, onFinish, error, value, onChange, customerRoles, handleDelete }
}

export default useCustomerUpdateViewModel
