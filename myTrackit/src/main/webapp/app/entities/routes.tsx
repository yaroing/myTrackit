import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import { ReducersMapObject, combineReducers } from '@reduxjs/toolkit';

import getStore from 'app/config/store';

import entitiesReducers from './reducers';

import Transfert from './transfert';
import TypeAction from './type-action';
import Staff from './staff';
import PointFocalPointService from './point-focal-point-service';
import SuiviMission from './suivi-mission';
import Action from './action';
import Zrosts from './zrosts';
import StockPointService from './stock-point-service';
import PointService from './point-service';
import Monitoring from './monitoring';
import RequetePartenaire from './requete-partenaire';
import StockPartenaire from './stock-partenaire';
import Mission from './mission';
import DetailsRequete from './details-requete';
import Partenaire from './partenaire';
import ItemVerifie from './item-verifie';
import Location from './location';
import RequetePointService from './requete-point-service';
import Section from './section';
import Transporteur from './transporteur';
import Catalogue from './catalogue';
import ItemTransfert from './item-transfert';
import PointFocalPartenaire from './point-focal-partenaire';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  const store = getStore();
  store.injectReducer('mytrackit', combineReducers(entitiesReducers as ReducersMapObject));
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="transfert/*" element={<Transfert />} />
        <Route path="type-action/*" element={<TypeAction />} />
        <Route path="staff/*" element={<Staff />} />
        <Route path="point-focal-point-service/*" element={<PointFocalPointService />} />
        <Route path="suivi-mission/*" element={<SuiviMission />} />
        <Route path="action/*" element={<Action />} />
        <Route path="zrosts/*" element={<Zrosts />} />
        <Route path="stock-point-service/*" element={<StockPointService />} />
        <Route path="point-service/*" element={<PointService />} />
        <Route path="monitoring/*" element={<Monitoring />} />
        <Route path="requete-partenaire/*" element={<RequetePartenaire />} />
        <Route path="stock-partenaire/*" element={<StockPartenaire />} />
        <Route path="mission/*" element={<Mission />} />
        <Route path="details-requete/*" element={<DetailsRequete />} />
        <Route path="partenaire/*" element={<Partenaire />} />
        <Route path="item-verifie/*" element={<ItemVerifie />} />
        <Route path="location/*" element={<Location />} />
        <Route path="requete-point-service/*" element={<RequetePointService />} />
        <Route path="section/*" element={<Section />} />
        <Route path="transporteur/*" element={<Transporteur />} />
        <Route path="catalogue/*" element={<Catalogue />} />
        <Route path="item-transfert/*" element={<ItemTransfert />} />
        <Route path="point-focal-partenaire/*" element={<PointFocalPartenaire />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
