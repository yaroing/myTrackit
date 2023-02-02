import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPointService } from 'app/shared/model/point-service.model';
import { getEntities as getPointServices } from 'app/entities/point-service/point-service.reducer';
import { IStockPointService } from 'app/shared/model/stock-point-service.model';
import { getEntity, updateEntity, createEntity, reset } from './stock-point-service.reducer';

export const StockPointServiceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pointServices = useAppSelector(state => state.mytrackit.pointService.entities);
  const stockPointServiceEntity = useAppSelector(state => state.mytrackit.stockPointService.entity);
  const loading = useAppSelector(state => state.mytrackit.stockPointService.loading);
  const updating = useAppSelector(state => state.mytrackit.stockPointService.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.stockPointService.updateSuccess);

  const handleClose = () => {
    navigate('/stock-point-service');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPointServices({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...stockPointServiceEntity,
      ...values,
      pointService: pointServices.find(it => it.id.toString() === values.pointService.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...stockPointServiceEntity,
          pointService: stockPointServiceEntity?.pointService?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.stockPointService.home.createOrEditLabel" data-cy="StockPointServiceCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.stockPointService.home.createOrEditLabel">Create or edit a StockPointService</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="stock-point-service-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.stockPointService.stockAnnee')}
                id="stock-point-service-stockAnnee"
                name="stockAnnee"
                data-cy="stockAnnee"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.stockPointService.stockMois')}
                id="stock-point-service-stockMois"
                name="stockMois"
                data-cy="stockMois"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.stockPointService.entreeMois')}
                id="stock-point-service-entreeMois"
                name="entreeMois"
                data-cy="entreeMois"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.stockPointService.sortieMois')}
                id="stock-point-service-sortieMois"
                name="sortieMois"
                data-cy="sortieMois"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.stockPointService.stockFinmois')}
                id="stock-point-service-stockFinmois"
                name="stockFinmois"
                data-cy="stockFinmois"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.stockPointService.stockDebut')}
                id="stock-point-service-stockDebut"
                name="stockDebut"
                data-cy="stockDebut"
                type="text"
              />
              <ValidatedField
                id="stock-point-service-pointService"
                name="pointService"
                data-cy="pointService"
                label={translate('myTrackitApp.stockPointService.pointService')}
                type="select"
              >
                <option value="" key="0" />
                {pointServices
                  ? pointServices.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/stock-point-service" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default StockPointServiceUpdate;
