import useCreateApi from '@/hooks/use-create-api'
import { CustomerFullRequest } from '@/model/Customer'
import { RadioChangeEvent } from 'antd'
import { v4 as uuidv4 } from 'uuid'
import dayjs from 'dayjs'
import { useEffect, useState } from 'react'
import CustomerConfigs from './CustomerConfigs'
import { useNavigate } from 'react-router-dom'
import useGetApi from '@/hooks/use-get-api'
import { CustomerRoleResponse } from '@/model/CustomerRole'
import CustomerRoleConfigs from '../customer-roles/CustomerRoleConfigs'

function useCustomerCreateViewModel() {
    const [value, setValue] = useState(0)
    const navigate = useNavigate()
    const { mutate: createCustomerApi } = useCreateApi<CustomerFullRequest>(CustomerConfigs.resourceUrl)

    const { data: customerRoles } = useGetApi<CustomerRoleResponse[]>(
        `${CustomerRoleConfigs.resourceUrl}/list-name`,
        CustomerRoleConfigs.resourceKey,
    )
    useEffect(() => {
        document.title = 'Add a new Customer - Vítore'
    }, [])

    const handleFinish = (values: CustomerFullRequest) => {
        const customerRequest: CustomerFullRequest = {
            customerGuid: uuidv4(),
            username: values.username,
            password: values.password,
            email: values.email,
            firstName: values.firstName,
            lastName: values.lastName,
            gender: values.gender,
            phone: values.phone,
            dateOfBirth: dayjs(values.dateOfBirth).toISOString(),
            hasShoppingCartItems: false,
            requireReLogin: false,
            failedLoginAttempts: 0,
            cannotLoginUntilDateUtc: '',
            active: false,
            deleted: false,
            lastLoginDateUtc: '',
            lastActivityDateUtc: '',
            customerRoles: values.customerRoles || [],
        }

        createCustomerApi(customerRequest, {
            onSuccess: () => {
                navigate(-1)
            },
        })
    }

    const onChange = (e: RadioChangeEvent) => {
        setValue(e.target.value)
    }

    return { value, onChange, handleFinish, customerRoles }
}
export default useCustomerCreateViewModel
