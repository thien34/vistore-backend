/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useState } from 'react'
import { DatePicker, Input, Button, Select } from 'antd'
import dayjs from 'dayjs'
import useGetApi from '@/hooks/use-get-api'
import { CustomerRoleResponse } from '@/model/CustomerRole'
import CustomerRoleConfigs from '../customer-roles/CustomerRoleConfigs'
import CustomerConfigs from './CustomerConfigs'

const { RangePicker } = DatePicker
const { Option } = Select

export type Filter = {
    email?: string
    firstName?: string
    lastName?: string
    registrationDateFrom?: string | null | undefined
    registrationDateTo?: string | null | undefined
    customerRoles?: string[]
}

interface CustomerSearchProps {
    search: (filter: Filter) => void
}

const CustomerSearch: React.FC<CustomerSearchProps> = ({ search }) => {
    const [filter, setFilter] = useState<Filter>({
        email: '',
        firstName: '',
        lastName: '',
        registrationDateFrom: null,
        registrationDateTo: null,
        customerRoles: [],
    })

    const { data: customerRoles, isLoading } = useGetApi<CustomerRoleResponse[]>(
        `${CustomerRoleConfigs.resourceUrl}/list-name`,
        CustomerRoleConfigs.resourceKey,
    )

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFilter({
            ...filter,
            [e.target.name]: e.target.value,
        })
    }

    const handleDateChange = (_dates: any, dateStrings: [string, string]) => {
        const startDate = dateStrings[0] ? dayjs(dateStrings[0]).startOf('day') : null // 00:00:00
        const endDate = dateStrings[1] ? dayjs(dateStrings[1]).endOf('day') : null // 23:59:59

        if (startDate && endDate && startDate.isSame(endDate, 'day')) {
            setFilter({
                ...filter,
                registrationDateFrom: startDate.toISOString(),
                registrationDateTo: endDate.toISOString(),
            })
        } else {
            setFilter({
                ...filter,
                registrationDateFrom: startDate ? startDate.toISOString() : null,
                registrationDateTo: endDate ? endDate.toISOString() : null,
            })
        }
    }

    const handleSearch = () => {
        search(filter)
    }

    return (
        <div className='bg-white p-6 rounded-lg shadow-md'>
            <div className='grid grid-cols-2 gap-4'>
                <div>
                    <label>Email</label>
                    <Input
                        placeholder='Enter email'
                        name='email'
                        value={filter.email}
                        onChange={handleInputChange}
                        size='large'
                    />
                </div>

                <div>
                    <label>First Name</label>
                    <Input
                        placeholder='Enter first name'
                        name='firstName'
                        value={filter.firstName}
                        onChange={handleInputChange}
                        size='large'
                    />
                </div>

                <div>
                    <label>Last Name</label>
                    <Input
                        placeholder='Enter last name'
                        name='lastName'
                        value={filter.lastName}
                        onChange={handleInputChange}
                        size='large'
                    />
                </div>

                <div>
                    <label>Registration Date</label>
                    <RangePicker
                        className='w-full'
                        value={[
                            filter.registrationDateFrom ? dayjs(filter.registrationDateFrom) : null,
                            filter.registrationDateTo ? dayjs(filter.registrationDateTo) : null,
                        ]}
                        onChange={handleDateChange}
                        format='YYYY-MM-DD'
                        size='large'
                    />
                </div>

                <div>
                    <label>Customer Roles</label>
                    <Select
                        mode='multiple'
                        size='large'
                        className='w-full'
                        value={filter.customerRoles}
                        onChange={(value) => setFilter({ ...filter, customerRoles: value })}
                        loading={isLoading}
                    >
                        {customerRoles?.map((role) => (
                            <Option key={role.id} value={role.id}>
                                {role.name}
                            </Option>
                        ))}
                    </Select>
                </div>
            </div>

            <div className='mt-6'>
                <Button className='bg-[#374151] border-[#374151] text-white' onClick={handleSearch} size='large'>
                    {CustomerConfigs.searchTitle}
                </Button>
            </div>
        </div>
    )
}

export default CustomerSearch
