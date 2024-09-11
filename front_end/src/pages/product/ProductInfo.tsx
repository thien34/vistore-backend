import { Collapse, Form, Input, Checkbox, DatePicker, Select } from 'antd'
import { useEffect, useState } from 'react'
import { useQuill } from 'react-quilljs'
import 'quill/dist/quill.snow.css'
import { useSelector, useDispatch } from 'react-redux'
import { RootState, AppDispatch } from '@/redux/store/productStore'
import { setProduct } from '@/slice/productSlice'
import useGetApi from '@/hooks/use-get-api'
import { CategoryNameResponse } from '@/model/Category'
import CategoryConfigs from '../category/CategoryConfigs'
import { ManufacturerResponseListName } from '@/model/Manufacturer'
import ManufactureConfigs from '../manufacturer/ManufactureConfigs'
import { ProductTagResponse } from '@/model/ProductTag'
import ProductTagConfigs from '../product-tag/ProductTagConfigs'
import useGetAllApi from '@/hooks/use-get-all-api'
import moment from 'moment'
import useProductCreateViewModel from './ProductCreate.vm'

const { Panel } = Collapse
const { TextArea } = Input

const ProductInfo = (): JSX.Element => {
    const { quill, quillRef } = useQuill({ placeholder: '' })
    const [isMarkAsNew, setIsMarkAsNew] = useState(false)
    const dispatch = useDispatch<AppDispatch>()
    const product = useSelector((state: RootState) => state.product)
    const {
        handleMarkAsNewChange,
        handleInputChange,
        handleCategoriesChange,
        handleManufacturerChange,
        handleProductTagsChange,
    } = useProductCreateViewModel()

    const { data: categories } = useGetApi<CategoryNameResponse[]>(
        CategoryConfigs.resourceUrlListName,
        CategoryConfigs.resourceKey,
    )

    const { data: manufacturers } = useGetApi<ManufacturerResponseListName[]>(
        ManufactureConfigs.resourceUrlListName,
        ManufactureConfigs.resourceKey,
    )

    const { data: productTags } = useGetAllApi<ProductTagResponse[]>(
        ProductTagConfigs.resourceUrl,
        ProductTagConfigs.resourceKey,
        {
            pageSize: 1000,
        },
    )

    useEffect(() => {
        if (quill) {
            quill.on('text-change', () => {
                const fullDescription = quill.root.innerHTML
                dispatch(setProduct({ lastContent: fullDescription }))
            })
        }
    }, [quill, dispatch])

    useEffect(() => {
        setIsMarkAsNew(product.markAsNew)
    }, [product.markAsNew])

    useEffect(() => {
        if (quill && product.fullDescription) {
            quill.clipboard.dangerouslyPasteHTML(product.fullDescription)
        }
    }, [quill, product.fullDescription])

    return (
        <Collapse defaultActiveKey={['1']} className='mb-6'>
            <Panel header='Product info' key='1'>
                <Form layout='vertical'>
                    <Form.Item label='Product name'>
                        <Input name='name' value={product.name ?? ''} onChange={handleInputChange} />
                        {product.errors.name && !product.name && (
                            <p className='mt-1 text-sm text-red-500'>{product.errors.name}</p>
                        )}
                    </Form.Item>
                    <Form.Item label='Short description'>
                        <TextArea
                            name='shortDescription'
                            rows={2}
                            value={product.shortDescription ?? ''}
                            onChange={handleInputChange}
                        />
                    </Form.Item>
                    <Form.Item label='Full description'>
                        <div style={{ height: 100 }}>
                            <div ref={quillRef} style={{ height: '100%' }} />
                        </div>
                    </Form.Item>
                    <Form.Item label='SKU' className='mt-14'>
                        <Input name='sku' value={product.sku} onChange={handleInputChange} />
                    </Form.Item>
                    <Form.Item label='Categories'>
                        <Select
                            mode='multiple'
                            style={{ width: '100%' }}
                            value={product.categoryIds ?? []}
                            options={categories?.map((category) => ({
                                value: category.id,
                                label: category.name,
                            }))}
                            onChange={handleCategoriesChange}
                        />
                    </Form.Item>
                    <Form.Item label='Manufacturers'>
                        <Select
                            mode='multiple'
                            style={{ width: '100%' }}
                            value={product.manufacturerIds ?? []}
                            options={manufacturers?.map((manufacturer) => ({
                                value: manufacturer.id,
                                label: manufacturer.manufacturerName,
                            }))}
                            onChange={handleManufacturerChange}
                        />{' '}
                    </Form.Item>
                    <Form.Item valuePropName='checked'>
                        <Checkbox
                            name='published'
                            checked={product.published}
                            onChange={(e) => dispatch(setProduct({ published: e.target.checked }))}
                        >
                            Published
                        </Checkbox>
                    </Form.Item>
                    <Form.Item label='Product tags'>
                        <Select
                            mode='tags'
                            style={{ width: '100%' }}
                            placeholder='Select or enter product tags'
                            value={product.productTags ?? []}
                            options={productTags?.items?.flat().map((productTag: ProductTagResponse) => ({
                                value: productTag.name,
                                label: productTag.name,
                            }))}
                            onChange={handleProductTagsChange}
                            tokenSeparators={[',']}
                        />
                    </Form.Item>
                    <Form.Item label='GTIN (global trade item number)'>
                        <Input name='gtin' value={product.gtin} onChange={handleInputChange} />
                    </Form.Item>
                    <Form.Item label='Manufacturer part number'>
                        <Input
                            name='manufacturePartNumber'
                            value={product.manufacturePartNumber}
                            onChange={handleInputChange}
                        />
                    </Form.Item>
                    <Form.Item name='showOnHomePage'>
                        <Checkbox
                            name='showOnHomePage'
                            checked={product.showOnHomePage}
                            onChange={(e) => dispatch(setProduct({ showOnHomePage: e.target.checked }))}
                        >
                            Show on home page
                        </Checkbox>
                    </Form.Item>
                    <Form.Item name='allowCustomerReviews'>
                        <Checkbox
                            name='allowCustomerReviews'
                            checked={product.allowCustomerReviews}
                            onChange={(e) => dispatch(setProduct({ allowCustomerReviews: e.target.checked }))}
                        >
                            Allow customer reviews
                        </Checkbox>
                    </Form.Item>
                    <Form.Item label='Available start date'>
                        <DatePicker
                            value={
                                product.availableStartDateTimeUtc
                                    ? moment(product.availableStartDateTimeUtc)
                                    : undefined
                            }
                            onChange={(date) =>
                                dispatch(setProduct({ availableStartDateTimeUtc: date?.toISOString() || undefined }))
                            }
                        />
                    </Form.Item>
                    <Form.Item label='Available end date'>
                        <DatePicker
                            value={
                                product.availableEndDateTimeUtc ? moment(product.availableEndDateTimeUtc) : undefined
                            }
                            onChange={(_, dateString) => {
                                if (typeof dateString === 'string') {
                                    dispatch(setProduct({ availableEndDateTimeUtc: dateString }))
                                }
                            }}
                        />
                    </Form.Item>
                    <Form.Item name='markAsNew'>
                        <Checkbox name='markAsNew' checked={product.markAsNew} onChange={handleMarkAsNewChange}>
                            Mark as new
                        </Checkbox>
                    </Form.Item>
                    {isMarkAsNew && (
                        <>
                            <Form.Item label='Mark as new. Start date'>
                                <DatePicker
                                    showTime
                                    value={
                                        product.markAsNewStartDateTimeUtc
                                            ? moment(product.markAsNewStartDateTimeUtc)
                                            : undefined
                                    }
                                    onChange={(_, dateString) => {
                                        if (typeof dateString === 'string') {
                                            dispatch(setProduct({ markAsNewStartDateTimeUtc: dateString }))
                                        } else {
                                            dispatch(setProduct({ markAsNewStartDateTimeUtc: undefined }))
                                        }
                                    }}
                                />
                            </Form.Item>
                            <Form.Item label='Mark as new. End date'>
                                <DatePicker
                                    showTime
                                    value={
                                        product.markAsNewEndDateTimeUtc
                                            ? moment(product.markAsNewEndDateTimeUtc)
                                            : undefined
                                    }
                                    onChange={(_, dateString) => {
                                        if (typeof dateString === 'string') {
                                            dispatch(setProduct({ markAsNewEndDateTimeUtc: dateString }))
                                        } else {
                                            dispatch(setProduct({ markAsNewEndDateTimeUtc: undefined }))
                                        }
                                    }}
                                />
                            </Form.Item>
                        </>
                    )}
                </Form>
            </Panel>
        </Collapse>
    )
}

export default ProductInfo
