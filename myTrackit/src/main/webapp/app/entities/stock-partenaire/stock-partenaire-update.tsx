import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStockPartenaire } from 'app/shared/model/stock-partenaire.model';
import { getEntity, updateEntity, createEntity, reset } from './stock-partenaire.reducer';

export const StockPartenaireUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const stockPartenaireEntity = useAppSelector(state => state.mytrackit.stockPartenaire.entity);
  const loading = useAppSelector(state => state.mytrackit.stockPartenaire.loading);
  const updating = useAppSelector(state => state.mytrackit.stockPartenaire.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.stockPartenaire.updateSuccess);

  const handleClose = () => {
    navigate('/stock-partenaire');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...stockPartenaireEntity,
      ...values,
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
          ...stockPartenaireEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.stockPartenaire.home.createOrEditLabel" data-cy="StockPartenaireCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.stockPartenaire.home.createOrEditLabel">Create or edit a StockPartenaire</Translate>
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
                  id="stock-partenaire-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.stockPartenaire.stockAnnee')}
                id="stock-partenaire-stockAnnee"
                name="stockAnnee"
                data-cy="stockAnnee"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.stockPartenaire.stockMois')}
                id="stock-partenaire-stockMois"
                name="stockMois"
                data-cy="stockMois"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.stockPartenaire.entreeMois')}
                id="stock-partenaire-entreeMois"
                name="entreeMois"
                data-cy="entreeMois"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.stockPartenaire.sortieMois')}
                id="stock-partenaire-sortieMois"
                name="sortieMois"
                data-cy="sortieMois"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.stockPartenaire.stockFinmois')}
                id="stock-partenaire-stockFinmois"
                name="stockFinmois"
                data-cy="stockFinmois"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.stockPartenaire.stockDebut')}
                id="stock-partenaire-stockDebut"
                name="stockDebut"
                data-cy="stockDebut"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/stock-partenaire" replace color="info">
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

export default StockPartenaireUpdate;
